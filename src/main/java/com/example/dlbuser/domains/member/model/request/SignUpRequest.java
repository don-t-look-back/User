package com.example.dlbuser.domains.member.model.request;

import com.example.dlbuser.domains.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignUpRequest {

    @Schema(description = "아이디", example = "daeun0426", required=true)
    private String memberId;

    @Schema(description = "비밀번호", example = "1234***", required=true)
    private String memberPassword;

    @Schema(description = "비밀번호 확인", example = "1234***", required=true)
    private String confirmPassword;

    @Schema(description = "이름", example = "곽다은", required=true)
    private String memberName;

    @Schema(description = "부서명", example = "서버개발")
    private String memberDepartment;

    @Schema(description = "연락처", example = "01012341234")
    private String memberContact;

    @Schema(description = "이메일", example = "email@naver.com")
    private String memberEmail;


    @Builder
    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberId(memberId)
                .memberPassword(passwordEncoder.encode(memberPassword))
                //.memberName(memberName)
                .build();
    }


}
