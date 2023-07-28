package com.tcha.review.repository;

import com.tcha.review.entity.Review;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);

    @Query("SELECT r FROM Review r WHERE r.trainer.id = :trainer_id")
    Page<Review> findAllByTrainerId(@Param("trainer_id") UUID trainerId, Pageable pageable);
    @Query("SELECT r FROM Review r WHERE r.userProfile.id = :user_id")
    Page<Review> findAllByUserId(@Param("user_id") Long userId, Pageable pageable);

}
