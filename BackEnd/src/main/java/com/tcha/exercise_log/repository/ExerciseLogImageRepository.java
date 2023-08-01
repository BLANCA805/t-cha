package com.tcha.exercise_log.repository;

import com.tcha.exercise_log.entity.ExerciseLogImage;
import com.tcha.review.entity.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseLogImageRepository extends JpaRepository<ExerciseLogImage, Long> {

    @Query("SELECT e FROM ExerciseLogImage e WHERE e.exerciseLog.id = :exercise_log_id")
    List<ExerciseLogImage> findAllImagesByExerciseLogId(
            @Param("exercise_log_id") Long exercise_log_id);

}
