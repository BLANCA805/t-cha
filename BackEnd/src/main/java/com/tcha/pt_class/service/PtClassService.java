package com.tcha.pt_class.service;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.dto.PtClassDto.Patch;
import com.tcha.pt_class.dto.PtClassDto.Response;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.mapper.PtClassMapper;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
    private final PtClassMapper ptClassMapper;

    public List<PtClassDto.Response> createPtClass(PtClassDto.Post postRequest) {

        // 요청을 보낸 트레이너가 유효한 트레이너인지 확인 (추후 에러 핸들링 추가)
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


    public void updatePtClass(String trainerId, Patch patchRequest) {
    }

    public Response findOnePtClass(String ptClassId) {

        return null;
    }

    public List<Response> findAllPtClasses() {

        return null;
    }

    public void deletePtClass(String trainerId, long ptClassId) {
    }
}
