package com.tcha.review.service;

import com.tcha.review.entity.Review;
import com.tcha.review.repository.ReviewRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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

    //트레이너 리뷰 저장
    @Transactional
    public Review createReview(Review review, String trainerId, Long userProfileId) {
        UUID saveTrainerId = UUID.fromString(trainerId);

        UserProfile userProfile = userProfileRepository.findById(userProfileId).get();
        Trainer trainer = trainerRepository.findById(saveTrainerId).get();

        review.setTrainer(trainer);
        review.setUserProfile(userProfile);

        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(keyMap.get("리뷰 수"), trainerId, String.valueOf(1));
        listOperations.leftPush(keyMap.get("평균 별점"), trainerId, String.valueOf(review.getStar()));

        if (listOperations.size(keyMap.get("리뷰 수")) >= 5) {
            ZSetCount(listOperations, trainerId);
        }

        if (listOperations.size(keyMap.get("평균 별점")) >= 5) {
            ZSetStar(listOperations, trainerId);
        }

        return reviewRepository.save(review);
    }

    public void ZSetStar(ListOperations<String, String> listOperations, String trainerId) {
//    public void ZSetStar(List<Float> listOperations, String trainerId) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        HashOperations<String, String, String> HashOperations = redisTemplate.opsForHash();

        System.out.println(Integer.parseInt(HashOperations.get(keyMap.get("리뷰 수"), trainerId)));
        Float sumStar = 0.0F;
        for (int i = 0; i < 5; i++) {
            sumStar += Float.parseFloat(listOperations.leftPop(trainerId));
        }
        System.out.println(Integer.parseInt(HashOperations.get(keyMap.get("리뷰 수"), trainerId)));
        ZSetOperations.add(keyMap.get("평균 별점"), trainerId,
                sumStar / Integer.parseInt(HashOperations.get(keyMap.get("리뷰 수"), trainerId)));
    }

    public void ZSetCount(ListOperations<String, String> listOperations, String trainerId) {
//    public void ZSetCount(List listOperations, String trainerId) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        HashOperations<String, String, String> HashOperations = redisTemplate.opsForHash();

        int cnt = 0;
        for (int i = 0; i < 5; i++) {
            String x = listOperations.leftPop(trainerId);
            System.out.println("x:" + x);
            cnt += Integer.parseInt(x);
        }

        ZSetOperations.incrementScore(keyMap.get("리뷰 수"), trainerId,
                cnt);
        HashOperations.put(keyMap.get("리뷰 수"), trainerId,
                HashOperations.get(keyMap.get("리뷰 수"), trainerId) + cnt);
    }


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


    //트레이너 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        Review findReview = findReview(id);
        reviewRepository.delete(findReview);
    }

}
