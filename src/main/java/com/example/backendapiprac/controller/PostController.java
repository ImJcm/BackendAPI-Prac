package com.example.backendapiprac.controller;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @GetMapping("/Posts")
    public ResponseEntity<ApiResponseDto> getPosts() {
        return postService.getPosts();
    }
}
