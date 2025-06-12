package com.aivle.agriculture.domain.test.service;

import com.aivle.agriculture.domain.test.dto.TestRequest;
import com.aivle.agriculture.domain.test.dto.TestResponse;

public interface TestService {

    TestResponse create(TestRequest request);

    TestResponse find(Long id);
    void update(Long id, TestRequest request);
}
