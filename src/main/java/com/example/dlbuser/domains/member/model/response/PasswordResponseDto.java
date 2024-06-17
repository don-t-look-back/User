package com.example.dlbuser.domains.member.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PasswordResponseDto {

    private Boolean status;

    private String message;

    public static PasswordResponseDto from(Boolean status){

        String message = "";
        if(status){
            message = "확인 성공";
        }else {
            message = "비밀번호를 확인해주세요";
        }

        return new PasswordResponseDtoBuilder()
                .status(status)
                .message(message)
                .build();
    }
}
