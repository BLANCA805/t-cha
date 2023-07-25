package com.tcha.exercise_log.repository;

import com.tcha.exercise_log.entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
//    ExerciseLog findByLiveId(Long id);

}
