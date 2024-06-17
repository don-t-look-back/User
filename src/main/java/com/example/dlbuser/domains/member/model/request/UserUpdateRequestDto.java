package com.example.dlbuser.domains.member.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserUpdateRequestDto {

    @Schema(description = "아이디", example = "hkdong")
    private String memberId;

    @Schema(description = "이름", example = "홍길동")
    private String memberName;

    @Schema(description = "부서명", example = "생산관리1과")
    private String memberDepartment;

    @Schema(description = "연락처", example = "010-555-6789")
    private String memberContact;

    @Schema(description = "이메일", example = "hkdong@naver.com")
    private String memberEmail;

    @Schema(description = "비밀번호", example = "●●●●●●", required = false)
    private String password;

    @Schema(description = "비밀번호 확인", example = "●●●●●●")
    private String passwordConfirm;

}
