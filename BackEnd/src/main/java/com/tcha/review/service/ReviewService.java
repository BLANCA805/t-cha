package com.tcha.review.service;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.review.dto.ReviewDto;
import com.tcha.review.dto.ReviewDto.Response;
import com.tcha.review.entity.Review;
import com.tcha.review.mapper.ReviewMapper;
import com.tcha.review.repository.ReviewRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import com.tcha.utils.pagination.MultiResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserProfileRepository userProfileRepository;
    private final TrainerRepository trainerRepository;
    private final PtLiveRepository ptLiveRepository;
    private final PtClassRepository ptClassRepository;

    private final ReviewMapper reviewMapper;

    private final RedisTemplate<String, String> redisTemplate;
    private final String STAR_KEY = "star";
    private final String REVIEW_KEY = "review";
    private final String PT_KEY = "PT";
    private final String BOOKMARK_KEY = "bookmark";
    private final String NEW_KEY = "new";
    private final Map<String, String> keyMap = new HashMap<>() {{
        put("평균 별점", STAR_KEY);
        put("리뷰 수", REVIEW_KEY);
        put("누적 PT 수", PT_KEY);
        put("즐겨찾기 수", BOOKMARK_KEY);
        put("최근", NEW_KEY);
    }};

    //Pagenation으로 트레이너 리뷰을 불러옴
    @Transactional(readOnly = true)
    public MultiResponseDto findReviewPages(int page, int size) {
        Page<Review> reviewPage = reviewRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").descending()));
        List<Review> reviews = reviewPage.getContent();
        List<Response> responses = new ArrayList<>();
        for(Review r : reviews) {
            PtLive ptLive = ptLiveRepository.findById(r.getPtLive().getId()).get();
            PtClass ptClass = ptClassRepository.findVerifiedClassById(ptLive.getPtClassId()).get();

            responses.add(reviewMapper.reviewToResponse(r,ptClass));
        }


        return new MultiResponseDto<>(responses, reviewPage);
    }

    //Pagenation으로 트레이너(해당 트레이너) 리뷰을 불러옴
    @Transactional(readOnly = true)
    public MultiResponseDto findReviewPagesByTrainerId(String trainerId, int page, int size) {
        Page<Review> reviewPage =reviewRepository.findAllByTrainerId(trainerId, PageRequest.of(page - 1, size, Sort.by("id").descending()));
        List<Review> reviews = reviewPage.getContent();
        List<Response> responses = new ArrayList<>();
        for(Review r : reviews) {
            PtLive ptLive = ptLiveRepository.findById(r.getPtLive().getId()).get();
            PtClass ptClass = ptClassRepository.findVerifiedClassById(ptLive.getPtClassId()).get();
            responses.add(reviewMapper.reviewToResponse(r,ptClass));
        }
        return new MultiResponseDto<>(responses, reviewPage);
    }

    //Pagenation으로 트레이너(해당 유저) 리뷰을 불러옴
    @Transactional(readOnly = true)
    public MultiResponseDto findReviewPagesByUserProfileId(Long userProfileId, int page, int size) {
        Page<Review> reviewPage = reviewRepository.findAllByUserProfileId(userProfileId, PageRequest.of(page - 1, size, Sort.by("id").descending()));
        List<Review> reviews = reviewPage.getContent();
        List<Response> responses = new ArrayList<>();
        for(Review r : reviews) {
            PtLive ptLive = ptLiveRepository.findById(r.getPtLive().getId()).get();
            PtClass ptClass = ptClassRepository.findVerifiedClassById(ptLive.getPtClassId()).get();
            responses.add(reviewMapper.reviewToResponse(r,ptClass));
        }
        return new MultiResponseDto<>(responses, reviewPage);
    }


    //트레이너 리뷰 1개 찾기
    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        return reviewRepository.findReviewById(id);
    }

    //트레이너 리뷰(ptLiveId) 1개 찾기
    @Transactional(readOnly = true)
    public Review findReviewByPtLiveId(Long ptLiveId) {
        return reviewRepository.findAllByPtLiveId(ptLiveId);
    }

    //트레이너 리뷰 저장
    @Transactional
    public ReviewDto.Response createReview(Review review) {

        String trainerId = review.getTrainer().getId();

        UserProfile userProfile = userProfileRepository.findById(review.getUserProfile().getId()).get();
        Trainer trainer = trainerRepository.findById(review.getTrainer().getId()).get();
        PtLive ptLive = findVerifiedByPtLiveId(review.getPtLive().getId());
        PtClass ptClass = ptClassRepository.findVerifiedClassById(ptLive.getPtClassId()).get();


        review.setTrainer(trainer);
        review.setUserProfile(userProfile);
        review.setPtLive(ptLive);

        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String starKey = "star:" + trainerId;
        String listKey = "list:" + trainerId;
        String reviewCountKey = "reviewCount:" + trainerId;
        if (valueOperations.get(reviewCountKey) == null) {
            valueOperations.set(reviewCountKey, "0");
        }
        if (valueOperations.get(starKey) == null) {
            valueOperations.set(starKey, "0");
        }

        listOperations.leftPush(listKey,
                String.valueOf(review.getStar()) + ":" + String.valueOf(1));

        if (listOperations.size(listKey) >= 1) {
            Zset();
        }

        Review savedReview = reviewRepository.save(review);
        ptLive.setReview(savedReview);


        ReviewDto.Response response = reviewMapper.reviewToResponse(savedReview,ptClass);

        return response;
    }


    @Scheduled(fixedDelay = 60*60*1000)
    public void Zset() {

        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        Set<String> keys = redisTemplate.keys("list:*");

        for (String s : keys) {
            String trainerId = s.split(":")[1];

            System.out.println(trainerId);
            String reviewCountKey = "reviewCount:" + trainerId;
            String starKey = "star:" + trainerId;
            String listKey = "list:" + trainerId;

            double sumStar = 0;
            double reviewCount = 0;
            for (int i = 0; i < listOperations.size(listKey); i++) {

                String[] strings = listOperations.leftPop(listKey).split(":");
                System.out.println(Arrays.toString(strings));

                sumStar += Double.parseDouble(strings[0]);
                reviewCount += Double.parseDouble(strings[1]);
            }
            Double totalStar = Double.parseDouble(valueOperations.get(starKey)) + sumStar;
            valueOperations.set(starKey, String.valueOf(totalStar));
            Double reviewConut = Double.parseDouble(valueOperations.get(reviewCountKey)) + reviewCount;
            valueOperations.set(reviewCountKey, String.valueOf(reviewConut));

            ZSetOperations.add(keyMap.get("평균 별점"), trainerId,
                    totalStar / reviewConut);
            ZSetOperations.add(keyMap.get("리뷰 수"), trainerId,
                    reviewConut);
        }
    }

    //트레이너 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        Review findReview = findReview(id);
        String trainerId = String.valueOf(findReview.getTrainer().getId());
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String reviewCountKey = "reviewCount:" + trainerId;
        String starKey = "star:" + trainerId;
        String listKey = "list:" + trainerId;
        if (valueOperations.get(reviewCountKey) == null) {
            valueOperations.set(reviewCountKey, "0");
        }
        if (valueOperations.get(starKey) == null) {
            valueOperations.set(starKey, "0");
        }

        listOperations.leftPush(listKey,
                String.valueOf(-1 * findReview.getStar()) + ":" + String.valueOf(-1));
        reviewRepository.delete(findReview);
    }

    public void deleteReviewAll() {
        reviewRepository.deleteAll();
    }

    //존재하는 ptlive인지에 대한 검증
    @Transactional
    public PtLive findVerifiedByPtLiveId(Long ptLiveId) throws BusinessLogicException {
        PtLive ptLive = ptLiveRepository.findById(ptLiveId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PT_LIVE_NOT_FOUND));
        return ptLive;
    }
}
