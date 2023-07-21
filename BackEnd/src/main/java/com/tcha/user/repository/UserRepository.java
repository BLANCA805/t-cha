package com.tcha.user.repository;

import com.tcha.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

/*
TODO

- entity id type 변경
 */