package com.tcha.exercise_log.repository;

import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.review.entity.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {

    @Query("SELECT e FROM ExerciseLog e WHERE e.ptLive.id = :ptLiveId")
    Optional<ExerciseLog> findByLiveId(Long ptLiveId);

    @Query("SELECT e FROM ExerciseLog e WHERE e.status = 'WRITE'")
    Optional<List<ExerciseLog>> findByStatus();


//    @Query("SELECT e FROM ExerciseLog e WHERE e.trainer.id = :trainer_id")
//    Page<Review> findAllByTrainerId(@Param("trainer_id") UUID trainerId, Pageable pageable);
//
//    @Query("SELECT e FROM ExerciseLog e WHERE e.userProfile.id = :user_profile_id")
//    Page<Review> findAllByUserId(@Param("user_profile_id") Long userProfileId,
//            Pageable pageable);
}
