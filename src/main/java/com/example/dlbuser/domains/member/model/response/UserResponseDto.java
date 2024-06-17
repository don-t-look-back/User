package com.example.dlbuser.domains.member.model.response;

import com.example.dlbuser.domains.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {

    @Schema(description = "id", example = "3")
    private Long id;

    @Schema(description = "아이디", example = "shlee")
    private String memberId;

    @Schema(description = "이름", example = "홍길동")
    private String memberName;

    @Schema(description = "부서명", example = "생산관리1과")
    private String memberDepartment;

    @Schema(description = "연락처", example = "010-555-6789")
    private String memberContact;

    @Schema(description = "이메일", example = "example@com")
    private String memberEmail;

    @Schema(description = "등록일자", example = "2022-10-27")
    private String createdAt;

    @Schema(description = "수정일자", example = "2023-10-27")
    private String updatedAt;

    @Schema(description = "관리자", example = "관리자: ADMIN, 그 외: MEMBER")
    private String isManager;

    @Schema(description = "가입승인", example = "가입요청: REQUEST, 승인: APPROVED, 승인거부: REJECTED")
    private String approvedState;

    public static UserResponseDto from(Member member){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return new UserResponseDtoBuilder()
                .id(member.getId())
                .memberId(member.getMemberId())
               // .memberName(member.getMemberName())
               // .memberEmail(member.getMemberEmail())
                .createdAt(member.getCreatedAt().format(formatter))
                .updatedAt(member.getUpdatedAt().format(formatter))
                .build();
    }
}
