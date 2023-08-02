package com.tcha.exercise_log.service;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.utils.upload.service.S3Uploader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final PtLiveRepository ptLiveRepository;
    private final ExerciseLogMapper exerciseLogMapper;


    //운동일지 저장
    @Transactional
    public ExerciseLog createExerciseLog(ExerciseLog exerciseLog, long ptLiveId) {
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();

        exerciseLog.setPtLive(ptLive);


        return exerciseLogRepository.save(exerciseLog);
    }



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

        // 이미지 테이블의 기존 정보 delete
        List<ExerciseLogImage> exerciseLogImagesList = exerciseLogImageRepository.findAllImagesByExerciseLogId(
                id);
        if (exerciseLogImagesList.size() != 0) {
            exerciseLogImageRepository.deleteAll(exerciseLogImagesList);
        }
        // 비디오 테이블의 기존 정보 delete
        List<ExerciseLogVideo> exerciseLogVideosList = exerciseLogVideoRepository.findAllVideosByExerciseLogId(
                id);
        if (exerciseLogVideosList.size() != 0) {
            exerciseLogVideoRepository.deleteAll(exerciseLogVideosList);
        }

        List<ExerciseLogImage> imgList = new ArrayList<>();
        List<ExerciseLogVideo> videoList = new ArrayList<>();

        if(imgPaths != null) {
            for (String img : imgPaths) {
                ExerciseLogImage exerciseLogImage = ExerciseLogImage.builder()
                        .imgURL(img)
                        .exerciseLog(findExerciseLog)
                        .build();
                imgList.add(exerciseLogImage);
            }
        }
        if(videoPaths != null) {
            for (String video : videoPaths) {
                ExerciseLogVideo exerciseLogVideo = ExerciseLogVideo.builder()
                        .video(video)
                        .exerciseLog(findExerciseLog)
                        .build();
                videoList.add(exerciseLogVideo);
            }
        }

        findExerciseLog.setExerciseImageList(exerciseLogImageRepository.saveAll(imgList));
        findExerciseLog.setExerciseVideoList(exerciseLogVideoRepository.saveAll(videoList));

        return exerciseLogRepository.save(findExerciseLog);

    }

    //운동일지 삭제
    @Transactional
    public void deleteExerciseLog(Long id) {

        ExerciseLog findExerciseLog = exerciseLogRepository.findById(id).get();
        // 이미지 테이블의 기존 정보 delete
        List<ExerciseLogImage> exerciseLogImagesList = exerciseLogImageRepository.findAllImagesByExerciseLogId(
                findExerciseLog.getId());
        if (exerciseLogImagesList != null) {
            for (ExerciseLogImage entity : exerciseLogImagesList) {
                s3Uploader.delete(entity.getImgURL());
            }
        }
        // 비디오 테이블의 기존 정보 delete
        List<ExerciseLogVideo> exerciseLogVideosList = exerciseLogVideoRepository.findAllVideosByExerciseLogId(
                findExerciseLog.getId());
        if (exerciseLogVideosList != null) {
            for (ExerciseLogVideo entity : exerciseLogVideosList) {
                s3Uploader.delete(entity.getVideo());
            }
        }

        exerciseLogRepository.delete(findExerciseLog);
    }

}
