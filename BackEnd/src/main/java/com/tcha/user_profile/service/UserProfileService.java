package com.tcha.user_profile.service;

import com.tcha.user.entity.User;
import com.tcha.user.service.UserService;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import java.util.Optional;
import java.util.UUID;
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
//        String userProfileId = UUID.randomUUID().toString();
//
//        userProfile.setId(userProfileId);

        return userProfileRepository.save(userProfile);

    }

    public UserProfile testUserProfile(String userId, UserProfile userProfile) {
        User foundUser = userService.findVerifiedUser(userId);

        UserProfile userProfileForSave = new UserProfile();

//        String userProfileId = UUID.randomUUID().toString();
//
//        userProfileForSave.setId(userProfileId);
        userProfileForSave.setUser(foundUser);

        Optional.ofNullable(userProfile.getName())
                .ifPresent(name -> userProfileForSave.setName(name));
        Optional.ofNullable(userProfile.getProfileImage())
                .ifPresent(profileImage -> userProfileForSave.setProfileImage(profileImage));

        return userProfileRepository.save(userProfileForSave);

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
}

/*
TODO
 */