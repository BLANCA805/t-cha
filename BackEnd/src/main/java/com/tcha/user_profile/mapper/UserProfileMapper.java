package com.tcha.user_profile.mapper;

import com.tcha.user.entity.User;
import com.tcha.user_profile.dto.UserProfileDto;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    default UserProfile postToUserProfile(UserProfileDto.Post postRequest) {

        User user = new User();
        user.setId(postRequest.getUserId());

        return UserProfile.builder()
                .user(user)
                .name(postRequest.getName())
                .profileImage(postRequest.getProfileImage())
                .build();
    }

    UserProfile patchToUserProfile(UserProfileDto.Patch patchRequest);

    default UserProfileDto.Response userProfileToResponse(UserProfile userProfile) {

        return UserProfileDto.Response
                .builder()
                .id(userProfile.getId())
                .name(userProfile.getName())
                .profileImage(userProfile.getProfileImage())
                .createdAt(userProfile.getCreatedAt())
                .modifiedAt(userProfile.getModifiedAt())
                .build();

    }

}
