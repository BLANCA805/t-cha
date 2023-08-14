package com.tcha.user.service;

import static com.tcha.user.entity.User.UserStatus.USER_QUIT;

import com.tcha.auth.utils.CustomAuthorityUtils;
import com.tcha.user.entity.User;
import com.tcha.user.repository.UserRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User testUser(String email) {

        User user = new User();
        List<String> authorities = authorityUtils.createRoles(email);

        user.setEmail(email);
        user.setRoles(authorities);

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public User findOneUser(String userId) {

        return findVerifiedUser(userId);
    }

    public User deleteOneUser(String userId) {

        User findUser = findVerifiedUser(userId);
        findUser.setStatus(USER_QUIT);

        return userRepository.save(findUser);
    }

    // DB에서 실제로 User를 찾는 로직
    public User findVerifiedUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        //Optional.orElseThrow() : 값이 존재하면(null이 아니면) 값을 반환하고, 값이 존재하지 않으면(null이면) 예외를 발생시킴.
        //customException 만들기
        User findUser = optionalUser.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return findUser;
    }

    // DB에서 email(unique)을 통해 User를 찾는 로직
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User findUser = optionalUser.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return findUser;
    }
}

/*
TODO
- createUser
    - UUID 사용법 확인
    - 권한 설정 로직 수정 => Spring Security 적용
 */