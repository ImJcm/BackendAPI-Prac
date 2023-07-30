package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.CommentRequestDto;
import com.example.backendapiprac.dto.UpdateCommentRequestDto;
import com.example.backendapiprac.entity.User;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    /* 댓글 등록 */
    ResponseEntity<ApiResponseDto> registComment(Long post_id, CommentRequestDto commentRequestDto, User user);

    /* 댓글 수정 */
    ResponseEntity<ApiResponseDto> updateComment(Long comment_id, UpdateCommentRequestDto updateCommentRequestDto, User user);

    /* 댓글 삭제 */
    ResponseEntity<ApiResponseDto> deleteComment(Long comment_id, User user);
}
