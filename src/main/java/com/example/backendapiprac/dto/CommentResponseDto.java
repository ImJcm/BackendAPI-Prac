package com.example.backendapiprac.dto;

import com.example.backendapiprac.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long comment_id;
    private Long post_id;
    //private Long user_id;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.comment_id = comment.getId();
        this.post_id = comment.getPost().getId();
        this.username = comment.getUser().getUsername();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
