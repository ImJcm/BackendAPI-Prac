package com.example.backendapiprac.controller;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.CommentRequestDto;
import com.example.backendapiprac.jwt.UserDetailsImpl;
import com.example.backendapiprac.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    /* 댓글 등록 */
    @PostMapping("/comment")
    public ResponseEntity<ApiResponseDto> registComment(@RequestParam Long post_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.registComment(post_id, commentRequestDto, userDetails.getUser());
    }
}
