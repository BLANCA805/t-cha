package com.tcha.trainer.mapper;

import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.dto.TrainerDto.Response;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    List<TrainerDto.ResponseList> trainerListToResponseListDtoList(List<Trainer> trainerList);

    default TrainerDto.ResponseList trainerToResponseListDto(Trainer trainer) {
        return TrainerDto.ResponseList.builder()
                .id(trainer.getId().toString())
                .introduction(trainer.getIntroduction())
                .tags(trainer.getTags())
                .profileImg(trainer.getUserProfile().getProfileImage())
                .profileName(trainer.getUserProfile().getName())
                .stars(3.5)
                .reviewCount(14)
                .createdAt(trainer.getCreatedAt().toLocalDate())
                .ptCount(20)
                .revisitGrade(0)
                .bookmarkCount(3)
                .build();
    }

    default TrainerDto.Response trainerToResponseDto(Trainer trainer) {
        return TrainerDto.Response.builder()
                .id(trainer.getId().toString())
                .introduction(trainer.getIntroduction())
                .tags(trainer.getTags())
                .title(trainer.getTitle())
                .content(trainer.getContent())
                .profileImg(trainer.getUserProfile().getProfileImage())
                .profileName(trainer.getUserProfile().getName())
                .build();
    }

    // mapper에서 다른 entity 가져오는거 괜찮은지 검색해보기
    default Trainer trainerPostDtoToTrainer(TrainerDto.Post postRequest, UserProfile postUser) {
        return Trainer.builder()
                .introduction(postRequest.getIntroduction())
                .tags(postRequest.getTags())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .userProfile(postUser)
                .build();
    }

}
