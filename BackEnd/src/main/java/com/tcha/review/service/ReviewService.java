package com.tcha.review.service;

import com.tcha.review.entity.Review;
import com.tcha.review.repository.ReviewRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserProfileRepository userProfileRepository;
    private final TrainerRepository trainerRepository;


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

        return reviewRepository.save(review);
    }


    //트레이너 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        Review findReview = findReview(id);
        reviewRepository.delete(findReview);
    }

}
