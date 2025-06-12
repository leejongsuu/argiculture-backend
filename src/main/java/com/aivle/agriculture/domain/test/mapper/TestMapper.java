package com.aivle.agriculture.domain.test.mapper;

import com.aivle.agriculture.domain.test.dto.TestRequest;
import com.aivle.agriculture.domain.test.dto.TestResponse;
import com.aivle.agriculture.domain.test.entity.Test;
import org.springframework.stereotype.Component;

@Component
public class TestMapper {

    public Test toEntity(TestRequest request) {
        return Test.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public TestResponse toResponse(Test test) {
        return TestResponse.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .build();
    }

    public void updateEntity(Test test, TestRequest request) {
        test.update(request.name(), request.description());
    }
}

