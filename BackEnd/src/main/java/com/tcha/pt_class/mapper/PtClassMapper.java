package com.tcha.pt_class.mapper;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtClassMapper {

    List<PtClassDto.Response> classListToclassResponseDtoList(List<PtClass> classList);

    default PtClass classPostDtoToClass(Trainer trainer, LocalDateTime startTime) {
        return PtClass.builder()
                .trainer(trainer)
                .startAt(startTime)
                .closeAt(startTime.plusHours(1))
                .build();
    }

    default PtClassDto.Response classToClassResponseDto(PtClass ptClass) {
        return PtClassDto.Response.builder()
                .trainerId(ptClass.getTrainer().getId().toString())
                .classId(ptClass.getId())
//                .liveId(ptClass.getPtLiveId())
                .startAt(ptClass.getStartAt())
                .build();
    }

}
