package com.aivle.agriculture.domain.test.controller;

import com.aivle.agriculture.domain.test.service.TestService;
import com.aivle.agriculture.global.response.Response;
import com.aivle.agriculture.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aivle.agriculture.global.response.SuccessCode.OK;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<Response<String>> test() {
        return responseFactory.success(OK, "result");
    }
}
