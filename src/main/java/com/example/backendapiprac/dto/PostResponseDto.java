package com.example.backendapiprac.dto;

import com.example.backendapiprac.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

}
