package com.example.backendapiprac.controller;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.PostRequestDto;
import com.example.backendapiprac.jwt.UserDetailsImpl;
import com.example.backendapiprac.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<ApiResponseDto> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/post")
    public ResponseEntity<ApiResponseDto> registPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.registPost(requestDto, userDetails.getUser());
    }
}
