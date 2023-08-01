package com.tcha.exercise_log.repository;

import com.tcha.exercise_log.entity.ExerciseLogVideo;
import com.tcha.exercise_log.entity.ExerciseLogVideo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseLogVideoRepository extends JpaRepository<ExerciseLogVideo, Long> {

    @Query("SELECT e FROM ExerciseLogVideo e WHERE e.exerciseLog.id = :exercise_log_id")
    List<ExerciseLogVideo> findAllVideosByExerciseLogId(
            @Param("exercise_log_id") Long exercise_log_id);
}
