package com.tcha.user_profile.mapper;

import com.tcha.user_profile.dto.UserProfileDto;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile postToUserProfile(UserProfileDto.Post postRequest);
    UserProfile patchToUserProfile(UserProfileDto.Patch patchRequest);
    UserProfileDto.Response userProfileToResponse(UserProfile userProfile);

}
