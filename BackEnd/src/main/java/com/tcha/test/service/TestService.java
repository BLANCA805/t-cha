package com.tcha.test.service;


import com.tcha.test.entity.Test;
import com.tcha.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final TestRepository testRepository;


    public Test createTest(Test test
            ) {


        return testRepository.save(test);
    }
}
