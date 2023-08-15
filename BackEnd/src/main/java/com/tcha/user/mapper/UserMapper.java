package com.tcha.user.mapper;

import com.tcha.user.dto.UserDto;
import com.tcha.user.dto.UserDto.LoginResponseDto;
import com.tcha.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto.Response userToResponse(User user);

    default UserDto.LoginResponseDto userToLoginDto(User user){

        UserDto.LoginResponseDto loginInfo = new LoginResponseDto();
        loginInfo.setUserId(user.getId());
        loginInfo.setUserProfileId(user.getUserProfile().getId());
        loginInfo.setName(user.getUserProfile().getName());
        loginInfo.setUserProfileImage(user.getUserProfile().getProfileImage());
        if(!user.getRoles().contains("TRAINER"))
            loginInfo.setTrainerId(null);
        else
            loginInfo.setTrainerId(user.getUserProfile().getTrainer().getId());


        return loginInfo;
    }

}

/*
TODO

- Dto, mapper 필요성 판단
 */
