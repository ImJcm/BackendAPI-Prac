package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.PostRequestDto;
import com.example.backendapiprac.dto.PostResponseDto;
import com.example.backendapiprac.entity.Post;
import com.example.backendapiprac.entity.User;
import com.example.backendapiprac.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public ResponseEntity<ApiResponseDto> getPosts() {
        List<PostResponseDto> postResponseDtoList = postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(),"전체 게시글 조회 성공",postResponseDtoList));
    }

    public ResponseEntity<ApiResponseDto> registPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(),"게시글 등록 성공"));
    }
}
