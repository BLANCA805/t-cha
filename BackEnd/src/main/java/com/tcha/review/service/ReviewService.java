package com.tcha.review.service;

import com.tcha.review.entity.Review;
import com.tcha.review.repository.ReviewRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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

        ZSetOperations.add(keyMap.get("평균 별점"), trainer.getId().toString(),
                review.getStar());

        System.out.println(ZSetOperations.size(STAR_KEY));
        return reviewRepository.save(review);
    }


    //트레이너 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        Review findReview = findReview(id);
        reviewRepository.delete(findReview);
    }

}
