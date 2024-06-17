package com.example.dlbuser.domains.member.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class MemberIdsRequestDto {

    @Schema(description = "업데이트할 사용자 id (pk) list", example = "[3, 4]", required = true)
    private List<Long> ids;
}