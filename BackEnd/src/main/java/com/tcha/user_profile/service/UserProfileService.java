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

    public UserProfile createUserProfile(UserProfile userProfile) {

        verifyUserProfile(userProfile);

        return userProfileRepository.save(userProfile);

    }

    public UserProfile updateUserProfile(Long userProfileId, UserProfile userProfile) {

        UserProfile userProfileForSave = findOneUserProfile(userProfileId);

        Optional.ofNullable(userProfile.getName())
                .ifPresent(name -> userProfileForSave.setName(name));
        Optional.ofNullable(userProfile.getProfileImage())
                .ifPresent(profileImage -> userProfileForSave.setProfileImage(profileImage));

        return userProfileRepository.save(userProfileForSave);

    }

    public UserProfile findOneUserProfile(Long userProfileId) {

        return findVerifiedUserProfile(userProfileId);
    }

    public UserProfile deleteOneUserProfile(Long userProfileId) {

        UserProfile userProfileForSave = findVerifiedUserProfile(userProfileId);

        userProfileForSave.setName(null); // 삭제 예정
        userProfileForSave.setProfileImage(null);

        return userProfileRepository.save(userProfileForSave);
    }

    public UserProfile findVerifiedUserProfile(Long userProfileId) {

        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userProfileId);

        UserProfile findUserProfile = optionalUserProfile.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_PROFILE_NOT_FOUND));

        return findUserProfile;
    }

    private void verifyUserProfile(UserProfile userProfile) {

        userService.findVerifiedUser(userProfile.getUser().getId());

    }

}

/*
TODO
 */