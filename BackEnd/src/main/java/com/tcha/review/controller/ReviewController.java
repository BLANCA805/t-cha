package com.tcha.review.controller;

import com.tcha.review.dto.ReviewDto;
import com.tcha.review.entity.Review;
import com.tcha.review.mapper.ReviewMapper;
import com.tcha.review.service.ReviewService;
import com.tcha.utils.pagination.MultiResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity postReview(@RequestBody ReviewDto.Post postRequest) {
        Review reviewToService = reviewMapper.postToReview(postRequest);
        Review reviewForResponse = reviewService.createReview(reviewToService);
        ReviewDto.Response response = reviewMapper.reviewToResponse(reviewForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getReviewPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<Review> reviewPage = reviewService.findReviewPages(page ,size);
        List<Review> reviews = reviewPage.getContent();
        List<ReviewDto.Response> responses = reviewMapper.reviewsToResponses(reviews);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, reviewPage),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneReview(@PathVariable(value = "id") Long id) {
        Review reviewForResponse = reviewService.findReview(id);
        ReviewDto.Response response = reviewMapper.reviewToResponse(reviewForResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOneReview(@PathVariable(value = "id") Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}