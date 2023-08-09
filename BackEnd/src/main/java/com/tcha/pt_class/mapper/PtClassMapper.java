package com.tcha.pt_class.mapper;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.dto.PtClassDto.Response;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtClassMapper {

    List<PtClassDto.Response> classListToClassResponseDtoList(List<PtClass> classList);

    default PtClass classPostDtoToClass(Trainer trainer, LocalDate date, LocalTime time) {
        return PtClass.builder()
                .trainer(trainer)
                .startDate(date)
                .startTime(time)
                .build();
    }

    default PtClassDto.Response classToClassResponseDto(PtClass ptClass) {
        return PtClassDto.Response.builder()
                .trainerId(ptClass.getTrainer().getId().toString())
                .classId(ptClass.getId())
                .liveId(ptClass.getPtLiveId())
                .startDate(ptClass.getStartDate())
                .startTime(ptClass.getStartTime())
                .build();
    }
}
