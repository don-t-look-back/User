package com.example.dlbuser.domains.auth.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "ID", example = "daeun0426", required=true)
    private String memberId;

    @Schema(description = "비밀번호", example = "1234***", required=true)
    private String memberPassword;
}
