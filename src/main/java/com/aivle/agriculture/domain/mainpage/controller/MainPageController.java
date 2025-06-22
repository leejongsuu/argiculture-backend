package com.aivle.agriculture.domain.mainpage.controller;

import com.aivle.agriculture.domain.mainpage.dto.MainPageResponseDto;
import com.aivle.agriculture.domain.mainpage.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping
    public MainPageResponseDto getMainPage() {
        return mainPageService.getMainPage();
    }
}

