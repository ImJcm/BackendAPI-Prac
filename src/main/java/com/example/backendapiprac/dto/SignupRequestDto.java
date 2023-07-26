package com.example.backendapiprac.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class SignupRequestDto {
    /* 닉네임은 최소 3자 이상, 알파벳 대소문자,숫자로 구성 */
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "닉네임이 올바르지 않습니다.")
    private String username;

    @Min(value = 4, message = "비밀번호는 4자 이상이여야 합니다.")
    private String password;

    @Min(value = 4,message = "비밀번호 체크는 4자 이상이여야 합니다.")
    private String checkpassword;
}
