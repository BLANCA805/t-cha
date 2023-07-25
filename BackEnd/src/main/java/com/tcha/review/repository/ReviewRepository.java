package com.tcha.review.repository;

import com.tcha.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);
}
