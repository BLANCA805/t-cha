package com.tcha.exercise_log.service;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.utils.upload.service.S3Uploader;
import java.util.ArrayList;
import java.util.List;
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
    private final TrainerRepository trainerRepository;
    private final PtLiveRepository ptLiveRepository;

    private final ExerciseLogMapper exerciseLogMapper;
    private final S3Uploader s3Uploader;


    //운동일지 저장
    @Transactional
    public ExerciseLog createExerciseLog(ExerciseLog exerciseLog, long ptLiveId) {
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();

        UUID trainerId = UUID.fromString(ptLive.getTrainerId());
        Trainer trainer = trainerRepository.findById(trainerId).get();
        exerciseLog.setPtLive(ptLive);
        exerciseLog.setTrainerName(trainer.getUserProfile().getName());

//        exerciseLog.setTrainerName(ptLive.getTrainerName());
        return exerciseLogRepository.save(exerciseLog);
    }
//    @Transactional
//    public ExerciseLog createExerciseLog(ExerciseLog exerciseLog, long ptLiveId) {
//        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();
//        UUID trainerId = UUID.fromString(ptLive.getTrainerId());
//        Trainer trainer = trainerRepository.findById(trainerId).get();
//
//        exerciseLog.setPtLive(ptLive);
//
//        exerciseLogMapper.exerciseLogToResponse(exerciseLog,trainer.getUserProfile().getName());
//
//        exerciseLogRepository.save(exerciseLog)
//        return ;
//    }


    //Pagenation으로 운동 일지를 불러옴
    @Transactional(readOnly = true)
    public Page<ExerciseLog> findExerciseLogPages(int page, int size) {

        return exerciseLogRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

    //운동일지 1개 찾기(PK)
    @Transactional(readOnly = true)
    public ExerciseLog findExerciseLog(Long id) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id).get();

        return exerciseLog;
    }

    //운동일지 1개 찾기(ptLiveId)
    @Transactional(readOnly = true)
    public ExerciseLog findExerciseLogByLiveId(Long liveId) {
        ExerciseLog exerciseLog = exerciseLogRepository.findByLiveId(liveId).get();

        return exerciseLog;
    }


    @Transactional
    public ExerciseLog updateExerciseLog(ExerciseLog exerciseLog, Long id) {

        ExerciseLog findExerciseLog = exerciseLogRepository.findById(id).get();

        findExerciseLog.setTitle(exerciseLog.getTitle());
        findExerciseLog.setContent(exerciseLog.getContent());

        List<String> imgList = findExerciseLog.getImages();

        // s3의 기존 정보 delete
        for (String s : imgList) {
            s3Uploader.delete(s);
        }
        List<String> videoList = findExerciseLog.getVideos();

        for (String s : videoList) {
            s3Uploader.delete(s);
        }

        findExerciseLog.setImages(exerciseLog.getImages());
        findExerciseLog.setVideos(exerciseLog.getVideos());

        return exerciseLogRepository.save(findExerciseLog);

    }

    //운동일지 삭제
    @Transactional
    public void deleteExerciseLog(Long id) {

        ExerciseLog findExerciseLog = exerciseLogRepository.findById(id).get();

        List<String> imgList = findExerciseLog.getImages();

        // s3의 기존 정보 delete
        for (String s : imgList) {
            s3Uploader.delete(s);
        }
        List<String> videoList = findExerciseLog.getVideos();

        for (String s : videoList) {
            s3Uploader.delete(s);
        }

        exerciseLogRepository.delete(findExerciseLog);
    }

}
