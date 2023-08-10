package com.tcha.user.controller;

import com.tcha.user.dto.UserDto;
import com.tcha.user.entity.User;
import com.tcha.user.mapper.UserMapper;
import com.tcha.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity postUser(@RequestParam String email) {

        User userForResponse = userService.testUser(email); //id 생성, 기본 상태 및 권한 설정
        UserDto.Response response = userMapper.userToResponse(userForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity getOneUser(@PathVariable("user-id") String userId) {
        User userForResponse = userService.findOneUser(userId);
        UserDto.Response response = userMapper.userToResponse(userForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //TODO 회원 상태 변경 로직
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteOneUser(@PathVariable("user-id") String userId) {
        userService.deleteOneUser(userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity loginUser(@RequestParam String email) {

        User foundUser = userService.findByEmail(email);
        UserDto.LoginResponseDto response = userMapper.userToLoginDto(foundUser);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}

/*
TODO
- post
    - 첫 구글 로그인 여부 확인
    - id 부여
    - 이메일 부여(google)
    - role 기본값 부여(user)
- patch
    - 권한 변경 필요 => 트레이너 등록 로직에 포함되는 것이 더 좋을듯.
- get
    - 개인 마인페이지 기능
- delete
    - 상태 변경(-> 탈퇴)
    - delete method 사용 =>

 - mapper 에러 표시줄 원인 찾기
 */