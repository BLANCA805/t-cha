package com.tcha.user_profile.controller;

import com.tcha.user.dto.UserDto;
import com.tcha.user.entity.User;
import com.tcha.user_profile.dto.UserProfileDto;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.mapper.UserProfileMapper;
import com.tcha.user_profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userProfiles/{user-id}")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserProfileMapper userProfileMapper;


    @PostMapping
    public ResponseEntity postUserProfile(@PathVariable("user-id") String userId, @RequestBody UserProfileDto.Post postRequest) {

        UserProfile userProfileForService = userProfileMapper.postToUserProfile(postRequest);
        UserProfile userProfileForResponse = userProfileService.testUserProfile(userId, userProfileForService);
        UserProfileDto.Response response = userProfileMapper.userProfileToResponse(userProfileForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    @PatchMapping
    public ResponseEntity patchUserProfile(@PathVariable("user-id") String userId, @RequestBody UserProfileDto.Patch patchRequest) {

        UserProfile userProfileForService = userProfileMapper.patchToUserProfile(patchRequest);
        UserProfile userProfileForResponse = userProfileService.updateUserProfile(userId, userProfileForService);
        UserProfileDto.Response response = userProfileMapper.userProfileToResponse(userProfileForResponse);

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity getOneUserProfile(@PathVariable("user-id") String userId) {
        UserProfile userProfileForResponse = userProfileService.findOneUserProfile(userId);
        UserProfileDto.Response response = userProfileMapper.userProfileToResponse(userProfileForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //TODO 회원 상태 변경 로직
    @DeleteMapping
    public ResponseEntity deleteOneUser(@PathVariable("user-id") String userId) {
        UserProfile userProfileForResponse = userProfileService.deleteOneUserProfile(userId);
        UserProfileDto.Response response = userProfileMapper.userProfileToResponse(userProfileForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}

/*
TODO
- post
    - 회원 가입 흐름에서 값을 넣은 상태 post 혹은 null default 로 생성
    - FK 지정, PK 생성, Dto 값 판단 및 설정
- patch
    - 프로필 이미지, 이름 수정
    - 이후 이름 수정은 제외될 수 있음
- get
    - 리뷰 등과 같은 곳, 즉 이름이랑 프로필 사진만 띄우는 곳에서 사용
    - 마이페이지는 어떻게 보여줄 지 고민
- delete
    - 프로필 이미지 삭제
    - 기본 값으로 변경
 */