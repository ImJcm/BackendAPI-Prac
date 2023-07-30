package com.example.backendapiprac.controller;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.LoginRequestDto;
import com.example.backendapiprac.dto.SignupRequestDto;
import com.example.backendapiprac.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    /* 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> onSignup(@RequestBody SignupRequestDto signupRequestDto) {
        return userServiceImpl.onSignup(signupRequestDto);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> onLogin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return userServiceImpl.onLogin(loginRequestDto, httpServletResponse);
    }
}
