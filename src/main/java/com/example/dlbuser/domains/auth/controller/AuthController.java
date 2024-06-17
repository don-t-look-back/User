package com.example.dlbuser.domains.auth.controller;

import com.example.dlbuser.common.response.BaseResponse;
import com.example.dlbuser.resolver.RequestMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.dlbuser.domains.auth.service.AuthService;
import com.example.dlbuser.domains.auth.model.response.TokenResponse;
import com.example.dlbuser.domains.auth.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Tag(name = "auth", description = "Auth API")
    @PostMapping("/login")
    @Operation(summary = "로그인 api")
    public BaseResponse<TokenResponse> login(@RequestBody LoginRequest request){

        return new BaseResponse<>(authService.login(request));
    }

    @Tag(name = "mypage", description = "마이페이지 API")
    @Operation(summary = "로그아웃 api", description = "- 토큰 필요")
    @PatchMapping("/logout")
    public BaseResponse<String> logout(@Parameter(hidden = true) @RequestMemberId Long memberId) {

        authService.logout(memberId);
        return new BaseResponse<>("로그아웃 성공");
    }

}
