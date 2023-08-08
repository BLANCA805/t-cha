package com.tcha.review.mapper;

import com.tcha.review.dto.ReviewDto;
import com.tcha.review.dto.ReviewDto.Response;
import com.tcha.review.entity.Review;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    default Review postToReview(ReviewDto.Post postRequest) {
        return Review.builder()
                .content(postRequest.getContent())
                .star(postRequest.getStar())
                .build();
    }

    Review getToReview(ReviewDto.Get getRequest);

    default ReviewDto.Response reviewToResponse(Review review) {
        return ReviewDto.Response.builder()
                .id(review.getId())
                .content(review.getContent())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .profileImg(review.getUserProfile().getProfileImage())
                .profileName(review.getUserProfile().getName())
                .trainerProfileImg(review.getTrainer().getUserProfile().getProfileImage())
                .trainerProfileName(review.getTrainer().getUserProfile().getName())
                .build();
    }

    List<Response> reviewsToResponses(List<Review> reviews);
}
