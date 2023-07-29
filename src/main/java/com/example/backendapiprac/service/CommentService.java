package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.CommentRequestDto;
import com.example.backendapiprac.dto.CommentResponseDto;
import com.example.backendapiprac.entity.Comment;
import com.example.backendapiprac.entity.Post;
import com.example.backendapiprac.entity.User;
import com.example.backendapiprac.repository.CommentRepository;
import com.example.backendapiprac.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /* 댓글 등록 */
    public ResponseEntity<ApiResponseDto> registComment(Long post_id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(post_id).orElse(null);

        if(post == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "해당 게시글이 존재하지 않습니다."));
        }

        Comment comment = new Comment(commentRequestDto);
        comment.setPost(post);
        comment.setUser(user);

        commentRepository.save(comment);

        CommentResponseDto newComment = new CommentResponseDto(comment);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "댓글 작성 성공",newComment));
    }
}
