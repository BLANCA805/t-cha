package com.tcha.pt_class.service;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.mapper.PtClassMapper;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PtClassService {

    private final PtClassRepository ptClassRepository;
    private final TrainerRepository trainerRepository;
    private final PtLiveRepository ptLiveRepository;
    private final PtClassMapper ptClassMapper;

    public List<PtClassDto.Response> createPtClass(PtClassDto.Post postRequest) {

        // 요청을 보낸 트레이너가 유효한 트레이너인지 확인 (에러 핸들링 추가)
        Trainer trainer = trainerRepository.findById(
                UUID.fromString(postRequest.getTrainerId())).orElseThrow();

        // pt 수업 등록할 날짜
        LocalDate date = postRequest.getDate();

        // 입력된 시간들에 대해서 ptClass 생성 (시간에 대한 유효성 검증 추가)
        for (LocalTime startTime : postRequest.getStartTimeList()) {
            ptClassRepository.save(
                    ptClassMapper.classPostDtoToClass(trainer, date.atTime(startTime)));
        }

        // 반환값으로 보낼 데이터 (선택된 날짜의 모든 수업)
        List<PtClass> classList =
                ptClassRepository.findClassByTrainerAndDate(trainer.getId(), date);

        return ptClassMapper.classListToclassResponseDtoList(classList);
    }

    public List<PtClassDto.Response> updatePtClass(PtClassDto.Patch patchRequest) {

        // 수정(==삭제)하고자 하는 수업에 대한 유효성 검증
        PtClass ptClass = ptClassRepository.findById(patchRequest.getClassId()).orElseThrow();

        // 1. 요청 트레이너의 수업인지 확인
        if (!ptClass.getTrainer().getId().toString().equals(patchRequest.getTrainerId())) {
            return null;
        }
        // 2. 이미 예약된 수업인지 확인
        if (ptClass.getPtLiveId() != null &&
                ptLiveRepository.findById(ptClass.getPtLiveId()).isPresent()) {
            return null;
        }

        // 수업 삭제(삭제 상태값 컬럼 변경)
        ptClass.setIsDel(1);

        return null; // 수정하기
    }

    public List<PtClassDto.Response> findPtClassByTrainer(String trainerId) {

        List<PtClass> trainerClassList = ptClassRepository.findClassByTrainer(trainerId);

        return ptClassMapper.classListToclassResponseDtoList(trainerClassList);
    }

    public List<PtClassDto.Response> findAllPtClasses() {

        List<PtClass> classList = ptClassRepository.findAll();

        return ptClassMapper.classListToclassResponseDtoList(classList);
    }

//    public void deletePtClass(String trainerId, long ptClassId) {
//    }
}
