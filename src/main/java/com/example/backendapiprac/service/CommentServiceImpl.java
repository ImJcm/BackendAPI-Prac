package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.CommentRequestDto;
import com.example.backendapiprac.dto.CommentResponseDto;
import com.example.backendapiprac.dto.UpdateCommentRequestDto;
import com.example.backendapiprac.entity.Comment;
import com.example.backendapiprac.entity.Post;
import com.example.backendapiprac.entity.User;
import com.example.backendapiprac.exception.NotFoundException;
import com.example.backendapiprac.exception.NotOwnerException;
import com.example.backendapiprac.repository.CommentRepository;
import com.example.backendapiprac.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /* 댓글 등록 */
    @Override
    public ResponseEntity<ApiResponseDto> registComment(Long post_id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(post_id).orElse(null);

        if(post == null) {
            throw new NotFoundException("해당 게시글이 존재하지 않습니다.");
        }

        Comment comment = new Comment(commentRequestDto);
        comment.setPost(post);
        comment.setUser(user);

        commentRepository.save(comment);

        CommentResponseDto newComment = new CommentResponseDto(comment);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "댓글 작성 성공",newComment));
    }

    /* 댓글 수정 */
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> updateComment(Long comment_id, UpdateCommentRequestDto updateCommentRequestDto, User user) {
        Comment comment = commentRepository.findById(comment_id).orElse(null);

        if(comment == null) {
            throw new NotFoundException("해당 댓글이 존재하지 않습니다");
        }

        if(comment.getUser().getId() != user.getId()) {
            throw new NotOwnerException("댓글 작성자가 아닙니다.");
        }

        comment.setContents(updateCommentRequestDto.getContents());

        CommentResponseDto newComment = new CommentResponseDto(comment);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "댓글 수정 완료",newComment));
    }

    /* 댓글 삭제 */
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteComment(Long comment_id, User user) {
        Comment comment = commentRepository.findById(comment_id).orElse(null);

        if(comment == null) {
            throw new NotFoundException("해당 댓글이 존재하지 않습니다");
        }

        if(comment.getUser().getId() != user.getId()) {
            throw new NotOwnerException("댓글 작성자가 아닙니다.");
        }

        commentRepository.delete(comment);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공"));
    }
}
