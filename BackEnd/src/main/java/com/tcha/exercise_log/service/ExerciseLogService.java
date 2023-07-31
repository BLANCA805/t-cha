package com.tcha.exercise_log.service;

import com.tcha.exercise_log.entity.ExerciseLogImage;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.review.entity.Review;
import java.util.Optional;
import java.util.UUID;
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
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;


    //Pagenation으로 운동 일지를 불러옴
    @Transactional(readOnly = true)
    public Page<ExerciseLog> findExerciseLogPages(int page, int size) {

        return exerciseLogRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

//    //Pagenation으로 해당 라이브 운동 일지를 불러옴
//    @Transactional(readOnly = true)
//    public Page<Review> findReviewPagesByTrainerId(UUID trainerId, int page, int size) {
//
//        return exerciseLogRepository.findAllByTrainerId(
//
//                trainerId, PageRequest.of(page - 1, size, Sort.by("id").descending()));
//    }
//


    //운동일지 1개 찾기
    @Transactional(readOnly = true)
    public ExerciseLog findExerciseLog(Long id) {
        Optional<ExerciseLog> exerciseLog = exerciseLogRepository.findById(id);

        return exerciseLog.get();
    }


    //운동일지 저장
    @Transactional
    public ExerciseLog createExerciseLog(ExerciseLog exerciseLog, String[] imgPaths) {

        for (String img : imgPaths) {
            ExerciseLogImage exerciseLogImage = ExerciseLogImage.builder()
                    .img(img)
                    .exerciseLog(exerciseLog)
                    .build();
            exerciseLog.getExerciseImageList().add(exerciseLogImage);
        }
        return exerciseLogRepository.save(exerciseLog);
    }

    @Transactional
    public ExerciseLog updateExerciseLog(ExerciseLog exerciseLog) {
        ExerciseLog findExerciseLog = findExerciseLog(exerciseLog.getId());

        findExerciseLog.setTitle(exerciseLog.getTitle());
        findExerciseLog.setContent(exerciseLog.getContent());

        return exerciseLogRepository.save(exerciseLog);

    }

    //운동일지 삭제
    @Transactional
    public void deleteExerciseLog(Long id) {

        ExerciseLog findExerciseLog = findExerciseLog(id);
        exerciseLogRepository.delete(findExerciseLog);
    }

}
