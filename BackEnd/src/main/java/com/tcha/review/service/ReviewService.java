package com.tcha.review.service;

import com.tcha.review.entity.Review;
import com.tcha.review.repository.ReviewRepository;
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
// TODO   private final UserService userService;


    //Pagenation으로 트레이너 리뷰을 불러옴
    @Transactional(readOnly = true)
    public Page<Review> findReviewPages(int page, int size) {

        return reviewRepository.findAll(

                PageRequest.of(page-1, size, Sort.by("id").descending()));
    }

    //트레이너 리뷰 1개 찾기
    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        return reviewRepository.findReviewById(id);
    }

    //트레이너 리뷰 저장
    @Transactional
    public Review createReview(Review review) {
//  TODO    memberService.findMember(Review.getMember().getMemberId());   // 존재하는 유저인지 확인 (관리자인지 확인도 필요)

        return reviewRepository.save(review);
    }


    //트레이너 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        /*
        TODO
            if (isValidAuthority(id)) {
                reviewRepository.deleteById(id);
                return;
            }
            throw new AuthenticationServiceException("삭제 권한 없음 :" + id);
        */
        Review findReview = findReview(id);
        reviewRepository.delete(findReview);
    }
    /*
     TODO
        private boolean isValidAuthority(Long reviewId) {
        Long userId = this.getReview(reviewId).getUser().getId();
        CustomUserDetails userDetails = UserDetailsUtil.get();

        if (userId.equals(userDetails.getId())) {
            return true;
        }

        return userDetails.hasAuthority(Authority.ADMIN);
        }
     */

}
