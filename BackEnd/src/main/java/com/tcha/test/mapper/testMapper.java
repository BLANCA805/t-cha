package com.tcha.test.mapper;


import com.tcha.test.dto.testDto;
import com.tcha.test.entity.test;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface testMapper {

    test postToTest(testDto.Post post.postRequest)
}
