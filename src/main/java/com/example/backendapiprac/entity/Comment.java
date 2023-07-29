package com.example.backendapiprac.entity;

import com.example.backendapiprac.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Comment")
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
    }
}
