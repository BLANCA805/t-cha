package com.tcha.test.mapper;


import com.tcha.notice.entity.Notice;
import com.tcha.test.dto.TestDto;
import com.tcha.test.entity.Test;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {

    Test postToTest(TestDto.Post postRequest);

    TestDto.Response testToResponse(Test test);

    List<TestDto.Response> testsToResponses(List<Test> tests);

}
