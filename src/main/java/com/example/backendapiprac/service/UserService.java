package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.LoginRequestDto;
import com.example.backendapiprac.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> onSignup(SignupRequestDto signupRequestDto);

    /* 로그인 */
    ResponseEntity<ApiResponseDto> onLogin(LoginRequestDto loginRequestDto, HttpServletResponse res);
}
