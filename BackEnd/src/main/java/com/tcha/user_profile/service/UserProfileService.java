package com.tcha.user_profile.service;

import com.tcha.user.entity.User;
import com.tcha.user.service.UserService;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserService userService;
    private final UserProfileRepository userProfileRepository;

    public UserProfile createUserProfile(String userId, UserProfile userProfile) {

        User user = new User();
        user.setId(userId);

        userProfile.setUser(user);

        Optional.ofNullable(userProfile.getName())
                .ifPresent(name -> userProfile.setName(name));
        Optional.ofNullable(userProfile.getProfileImage())
                .ifPresent(profileImage -> userProfile.setProfileImage(profileImage));

        return userProfileRepository.save(userProfile);

    }

    public UserProfile updateUserProfile(String userId, UserProfile userProfile) {

        User foundUser = userService.findVerifiedUser(userId);
        UserProfile userProfileForSave = foundUser.getUserProfile();

        Optional.ofNullable(userProfile.getName())
                .ifPresent(name -> userProfileForSave.setName(name));
        Optional.ofNullable(userProfile.getProfileImage())
                .ifPresent(profileImage -> userProfileForSave.setProfileImage(profileImage));

        return userProfileRepository.save(userProfileForSave);

    }

    public UserProfile findOneUserProfile(String userId) {

        User foundUser = userService.findVerifiedUser(userId);
        UserProfile userProfile = foundUser.getUserProfile();

        return userProfile;
    }

    public UserProfile deleteOneUserProfile(String userId) {

        User foundUser = userService.findVerifiedUser(userId);
        UserProfile userProfileForSave = foundUser.getUserProfile();

        userProfileForSave.setName(null);
        userProfileForSave.setProfileImage(null);

        return userProfileRepository.save(userProfileForSave);
    }

    public UserProfile findVerifiedUserProfile(Long userProfileId) {

        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userProfileId);

        UserProfile findUserProfile = optionalUserProfile.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_PROFILE_NOT_FOUND));

        return findUserProfile;
    }

}

/*
TODO
 */