package com.tcha.review.controller;

import com.tcha.review.dto.ReviewDto.Post;
import com.tcha.review.dto.ReviewDto.Response;
import com.tcha.review.entity.Review;
import com.tcha.review.mapper.ReviewMapper;
import com.tcha.review.service.ReviewService;
import com.tcha.utils.pagination.MultiResponseDto;
import java.util.List;
import java.util.UUID;
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
    public ResponseEntity<Response> postReview(
            @RequestBody Post postRequest) {
        Review reviewToService = reviewMapper.postToReview(postRequest);
//        Review reviewForResponse = reviewService.createReview(reviewToService);
//        Response response = reviewMapper.reviewToResponse(reviewForResponse);
        Response response = reviewService.createReview(reviewToService);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto> getReviewPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

//        Page<Review> reviewPage = reviewService.findReviewPages(page, size);
//        List<Review> reviews = reviewPage.getContent();
//        List<Response> responses = reviewService.findReviewPages(page-1, size);

        return new ResponseEntity<>(reviewService.findReviewPages(page, size), HttpStatus.OK);
    }

    @GetMapping("/trainer/{trainer-id}")
    public ResponseEntity<MultiResponseDto> getReviewPageByTrainerId(
            @PathVariable(value = "trainer-id") String trainerID,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

//        Page<Review> reviewPage = reviewService.findReviewPagesByTrainerId(trainerID, page, size);
//        List<Review> reviews = reviewPage.getContent();
//        List<Response> responses = reviewMapper.reviewsToResponses(reviews);

        return new ResponseEntity<>(reviewService.findReviewPagesByTrainerId(trainerID,page, size), HttpStatus.OK);
    }

    @GetMapping("/user/{user-profile-id}")
    public ResponseEntity<MultiResponseDto> getReviewPageByUserId(
            @PathVariable(value = "user-profile-id") Long userProfileID,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

//        Page<Review> reviewPage = reviewService.findReviewPagesByUserProfileId(userProfileID, page,
//                size);
//        List<Review> reviews = reviewPage.getContent();
//        List<Response> responses = reviewMapper.reviewsToResponses(reviews);

        return new ResponseEntity<>(reviewService.findReviewPagesByUserProfileId(userProfileID,page, size), HttpStatus.OK);
    }


    @GetMapping("/{review-id}")
    public ResponseEntity<Response> getOneReview(@PathVariable(value = "review-id") Long id) {
        Review reviewForResponse = reviewService.findReview(id);
//        Response response = reviewMapper.reviewToResponse(reviewForResponse);
        Response response = reviewService.createReview(reviewForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }
    @GetMapping("/ptlive/{pt-live-id}")
    public ResponseEntity<Response> getOneReviewByPtLiveId(@PathVariable(value = "ptlive-id") Long ptLiveId) {
        Review reviewForResponse = reviewService.findReviewByPtLiveId(ptLiveId);
//        Response response = reviewMapper.reviewToResponse(reviewForResponse);
        Response response = reviewService.createReview(reviewForResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{review-id}")
    public ResponseEntity deleteOneReview(@PathVariable(value = "review-id") Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}