package com.tcha.exercise_log.service;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.dto.ExerciseLogDto.Response;
import com.tcha.exercise_log.entity.ExerciseLogImage;
import com.tcha.exercise_log.entity.ExerciseLogVideo;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.repository.ExerciseLogImageRepository;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.repository.ExerciseLogVideoRepository;
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
    private final ExerciseLogImageRepository exerciseLogImageRepository;
    private final ExerciseLogVideoRepository exerciseLogVideoRepository;
    private final S3Uploader s3Uploader;

    private final ExerciseLogMapper exerciseLogMapper;


    //운동일지 저장
    @Transactional
    public ExerciseLog createExerciseLog(ExerciseLog exerciseLog, String[] imgPaths,
            String[] videoPaths) {

        List<ExerciseLogImage> imgList = new ArrayList<>();
        List<ExerciseLogVideo> videoList = new ArrayList<>();

        if (imgPaths != null) {
            for (String img : imgPaths) {
                ExerciseLogImage exerciseLogImage = ExerciseLogImage.builder()
                        .imgURL(img)
                        .exerciseLog(exerciseLog)
                        .build();
                imgList.add(exerciseLogImage);
            }
        }

        if (videoPaths != null) {
            for (String video : videoPaths) {
                ExerciseLogVideo exerciseLogVideo = ExerciseLogVideo.builder()
                        .video(video)
                        .exerciseLog(exerciseLog)
                        .build();
                videoList.add(exerciseLogVideo);
            }
        }

        exerciseLog.setExerciseImageList(exerciseLogImageRepository.saveAll(imgList));
        exerciseLog.setExerciseVideoList(exerciseLogVideoRepository.saveAll(videoList));
        return exerciseLogRepository.save(exerciseLog);
    }



    //Pagenation으로 운동 일지를 불러옴
    @Transactional(readOnly = true)
    public Page<ExerciseLog> findExerciseLogPages(int page, int size) {

        return exerciseLogRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

    //운동일지 1개 찾기
    @Transactional(readOnly = true)
    public ExerciseLog findExerciseLog(Long id) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id).get();

        return exerciseLog;
    }

//    @Transactional(readOnly = true)
//    public ExerciseLog findExerciseLogByLiveId(Long liveId) {
//        Optional<ExerciseLog> exerciseLog = exerciseLogRepository.findByLiveId(liveId);
//
//        return exerciseLog.get();
//    }


    @Transactional(readOnly = true)
    public ExerciseLogDto.Response getExerciseLog(Long id) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id).get();

        //image 넣기
        List<String> imgPaths = new ArrayList<>();
        List<ExerciseLogImage> exerciseLogImagesList = exerciseLogImageRepository.findAllImagesByExerciseLogId(
                id);
        if (exerciseLogImagesList.size() != 0) {
            for (ExerciseLogImage img : exerciseLogImagesList) {
                imgPaths.add(img.getImgURL());
            }
        }

        //video 넣기
        List<String> videoPaths = new ArrayList<>();
        List<ExerciseLogVideo> exerciseLogVideosList = exerciseLogVideoRepository.findAllVideosByExerciseLogId(
                id);
        if (exerciseLogVideosList.size() != 0) {
            for (ExerciseLogVideo video : exerciseLogVideosList) {
                videoPaths.add(video.getVideo());
            }
        }

        //response 만들기(List<String> images에 넣는 것 때문에 Mapper에 넣기 애매)
        return exerciseLogMapper.exerciseLogToResponse(exerciseLog,imgPaths,videoPaths);
    }



    @Transactional
    public ExerciseLog updateExerciseLog(Long id,ExerciseLog exerciseLog, String[] imgPaths,
            String[] videoPaths) {

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
