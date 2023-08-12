package com.tcha.review.mapper;

import com.tcha.pt_live.entity.PtLive;
import com.tcha.review.dto.ReviewDto;
import com.tcha.review.dto.ReviewDto.Response;
import com.tcha.review.entity.Review;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    default Review postToReview(ReviewDto.Post postRequest) {

        UserProfile userProfile = new UserProfile();
        userProfile.setId(postRequest.getUserId());

        Trainer trainer = new Trainer();
        trainer.setId(postRequest.getTrainerId());

        PtLive ptLive = new PtLive();
        ptLive.setId(postRequest.getPtLiveId());

        return Review.builder()
                .content(postRequest.getContent())
                .star(postRequest.getStar())
                .ptLive(ptLive)
                .userProfile(userProfile)
                .trainer(trainer)
                .build();
    }

    Review getToReview(ReviewDto.Get getRequest);

    default ReviewDto.Response reviewToResponse(Review review) {
        return ReviewDto.Response.builder()
                .id(review.getId())
                .content(review.getContent())
                .star(review.getStar())
                .profileImg(review.getUserProfile().getProfileImage())
                .profileName(review.getUserProfile().getName())
                .trainerProfileImg(review.getTrainer().getUserProfile().getProfileImage())
                .trainerProfileName(review.getTrainer().getUserProfile().getName())
                .build();
    }

    List<Response> reviewsToResponses(List<Review> reviews);
}
