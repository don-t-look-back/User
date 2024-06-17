package com.example.dlbuser.domains.member.model.response;

import com.example.dlbuser.domains.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class UserDetailResponseDto {

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

//    @Schema(description = "비밀번호", example = "●●●●●●")
//    private String password;
//
//    @Schema(description = "비밀번호 확인", example = "●●●●●●")
//    private String passwordConfirm;
//
//    public static UserDetailResponseDto from(Member member){
//
//        return new UserDetailResponseDtoBuilder()
//                .memberId(member.getMemberId())
//                .memberName(member.getMemberName())
//                .memberDepartment(member.getMemberDepartment())
//                .memberContact(member.getMemberContact())
//                .memberEmail(member.getMemberEmail())
////                .password("●●●●●●")
////                .passwordConfirm("●●●●●●")
//                .build();
//    }
}
