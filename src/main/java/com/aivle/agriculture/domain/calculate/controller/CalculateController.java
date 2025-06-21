package com.aivle.agriculture.domain.calculate.controller;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CalculateResponse;
import com.aivle.agriculture.domain.calculate.service.CalculateService;
import com.aivle.agriculture.global.response.Response;
import com.aivle.agriculture.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calc")
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateService calculateService;

    @PostMapping
    public Response<CalculateResponse> calculate(@RequestBody CalculateRequest request) {
        return Response.of(SuccessCode.OK, calculateService.calculate(request));
    }
}
