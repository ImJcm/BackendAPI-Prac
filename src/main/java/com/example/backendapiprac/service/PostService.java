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

    /* 게시글 전체 조회 */
    private final PostRepository postRepository;
    public ResponseEntity<ApiResponseDto> getPosts() {
        List<PostResponseDto> postResponseDtoList = postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(),"전체 게시글 조회 성공",postResponseDtoList));
    }

    /* 게시글 등록 */
    public ResponseEntity<ApiResponseDto> registPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(),"게시글 등록 성공"));
    }

    /* 게시글 조회 */
    public ResponseEntity<ApiResponseDto> getPost(Long post_id) {
        Post post = postRepository.findById(post_id).orElse(null);

        if(post == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글 존재하지 않습니다."));
        }

        PostResponseDto newPost = new PostResponseDto(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(),"게시글 조회 성공", newPost));
    }
}
