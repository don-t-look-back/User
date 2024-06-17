package com.example.dlbuser.domains.member.controller;

import com.example.dlbuser.common.response.BaseResponse;
import com.example.dlbuser.domains.auth.model.response.TokenResponse;
import com.example.dlbuser.domains.member.model.request.*;
import com.example.dlbuser.domains.member.model.response.PasswordResponseDto;
import com.example.dlbuser.domains.member.service.MemberService;

import com.example.dlbuser.resolver.RequestMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/member")
    @Tag(name = "auth", description = "Auth API")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입 api")
    public BaseResponse<TokenResponse> signUp(@RequestBody @Valid SignUpRequest request) {

        return new BaseResponse<>(memberService.signUp(request));
    }


    @PostMapping("/mypage/password")
    @Tag(name = "mypage", description = "마이페이지 API")
    @Operation(summary = "비밀번호 입력 api", description = "true: 확인 성공 / false: 비밀번호를 확인해주세요\n" + "토큰 필요")
    public BaseResponse<PasswordResponseDto> checkPassword(@Parameter(hidden = true) @RequestMemberId Long memberId,
                                                    @RequestParam(value = "password", required = true) String password) {

        return new BaseResponse<>(PasswordResponseDto.from(memberService.checkPassword(memberId, password)));
    }

}
