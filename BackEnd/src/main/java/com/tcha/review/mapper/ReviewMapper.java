package com.tcha.review.mapper;

import com.tcha.review.dto.ReviewDto;
import com.tcha.review.dto.ReviewDto.Response;
import com.tcha.review.entity.Review;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review postToReview(ReviewDto.Post postRequest);

    ReviewDto.Response reviewToResponse(Review review);

    List<Response> reviewsToResponses(List<Review> reviews);
}
