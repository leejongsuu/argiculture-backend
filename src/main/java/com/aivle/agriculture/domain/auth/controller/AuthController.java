package com.aivle.agriculture.domain.auth.controller;

import com.aivle.agriculture.domain.auth.service.AuthService;
import com.aivle.agriculture.domain.auth.service.dto.TokenInfo;
import com.aivle.agriculture.global.response.Response;
import com.aivle.agriculture.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.aivle.agriculture.global.response.SuccessCode.OK;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseFactory responseFactory;

    @GetMapping("/login/{provider}")
    public String login(@PathVariable String provider) {
        String redirectUrl = authService.login(provider);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/success")
    @ResponseBody
    public ResponseEntity<Response<TokenInfo>> success(@RequestParam String type,
                                                       @RequestParam String accessToken,
                                                       @RequestParam long accessExpireDuration
    ) {
        TokenInfo tokenInfo = TokenInfo.builder()
                .type(type)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessExpireDuration)
                .build();
        return responseFactory.success(OK, tokenInfo);
    }

    @GetMapping("/failure") // TODO : 임시로 만든 페이지
    public String failure() {
        return "failure";
    }
}
