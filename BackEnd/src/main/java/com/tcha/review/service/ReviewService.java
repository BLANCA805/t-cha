package com.tcha.review.service;

import com.tcha.review.entity.Review;
import com.tcha.review.repository.ReviewRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserProfileRepository userProfileRepository;
    private final TrainerRepository trainerRepository;
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
    public Page<Review> findReviewPages(int page, int size) {

        return reviewRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

    //Pagenation으로 트레이너(해당 트레이너) 리뷰을 불러옴
    @Transactional(readOnly = true)
    public Page<Review> findReviewPagesByTrainerId(String trainerId, int page, int size) {

        UUID TrainerId = UUID.fromString(trainerId);
        return reviewRepository.findAllByTrainerId(

                TrainerId, PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

    //Pagenation으로 트레이너(해당 유저) 리뷰을 불러옴
    @Transactional(readOnly = true)
    public Page<Review> findReviewPagesByUserProfileId(Long userProfileId, int page, int size) {

        return reviewRepository.findAllByUserProfileId(

                userProfileId, PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }


    //트레이너 리뷰 1개 찾기
    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        return reviewRepository.findReviewById(id);
    }

    //트레이너 리뷰 저장
    @Transactional
    public Review createReview(Review review, String trainerId, Long userProfileId) {
        UUID saveTrainerId = UUID.fromString(trainerId);

        UserProfile userProfile = userProfileRepository.findById(userProfileId).get();
        Trainer trainer = trainerRepository.findById(saveTrainerId).get();

        review.setTrainer(trainer);
        review.setUserProfile(userProfile);


        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String valueKey = "count:"+trainerId;
        String starKey = "star:"+trainerId;
        String listKey = "list:"+trainerId;
        if (valueOperations.get(valueKey) == null){
            valueOperations.set(valueKey,"0");
        }
        if (valueOperations.get(starKey) == null){
            valueOperations.set(starKey,"0");
        }
        List<Map<String,String>> list = new ArrayList<>();
        listOperations.leftPush(listKey, String.valueOf(review.getStar()));

        System.out.println(redisTemplate.keys("count:"));
        if(listOperations.size(listKey) >= 5) {
            Zset();
        }

        return reviewRepository.save(review);
    }

    public void ReviewCount(String valueKey, long num){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        int x = Integer.parseInt(valueOperations.get(valueKey));
        valueOperations.set(valueKey, String.valueOf(x + num));
    }

    @Scheduled(fixedDelay = 3000)
    public void Zset() {

        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        Set<String> keys = redisTemplate.keys("list:");

        System.out.println(keys);
        for (String s : keys) {
            String trainerId = s.split(":")[1];

            System.out.println(trainerId);
            String valueKey = "count:" + trainerId;
            String starKey = "star:" + trainerId;
            String listKey = "list:" + trainerId;

            double sumStar = 0;

            for (int i = 0; i < listOperations.size(listKey); i++) {
                sumStar += Double.parseDouble(listOperations.leftPop(listKey));
            }
            ReviewCount(valueKey, listOperations.size(listKey));
            Double totalStar = Double.parseDouble(valueOperations.get(starKey)) + sumStar;
            valueOperations.set(starKey, String.valueOf(totalStar));
            Double reviewConut = Double.parseDouble(valueOperations.get(valueKey));

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
        reviewRepository.delete(findReview);
    }

}
