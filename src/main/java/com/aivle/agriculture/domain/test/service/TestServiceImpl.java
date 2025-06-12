package com.aivle.agriculture.domain.test.service;

import com.aivle.agriculture.domain.test.dto.TestRequest;
import com.aivle.agriculture.domain.test.dto.TestResponse;
import com.aivle.agriculture.domain.test.entity.Test;
import com.aivle.agriculture.domain.test.mapper.TestMapper;
import com.aivle.agriculture.domain.test.repository.TestRepository;
import com.aivle.agriculture.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.aivle.agriculture.global.response.ErrorCode.NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public TestResponse create(TestRequest request) {
        Test test = testMapper.toEntity(request);
        testRepository.save(test);
        return testMapper.toResponse(test);
    }

    @Transactional(readOnly = true)
    @Override
    public TestResponse find(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND));
        return testMapper.toResponse(test);
    }

    @Override
    public void update(Long id, TestRequest request) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND));
        testMapper.updateEntity(test, request);
    }
}
