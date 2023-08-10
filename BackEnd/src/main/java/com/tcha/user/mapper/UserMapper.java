package com.tcha.user.mapper;

import com.tcha.user.dto.UserDto;
import com.tcha.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto.Response userToResponse(User user);

    default UserDto.LoginResponseDto userToLoginDto(User user){

        return UserDto.LoginResponseDto.builder()
                .userId(user.getId())
                .userProfileId(user.getUserProfile().getId())
                .name(user.getUserProfile().getName())
                .userProfileImage(user.getUserProfile().getProfileImage())
                .trainerId(user.getUserProfile().getTrainer().getId())
                .build();
    }

}

/*
TODO

- Dto, mapper 필요성 판단
 */
