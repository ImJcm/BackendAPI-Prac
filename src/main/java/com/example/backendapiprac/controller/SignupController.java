package com.example.backendapiprac.controller;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.SignupRequestDto;
import com.example.backendapiprac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> onSignup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.onSignup(signupRequestDto);
    }
}
