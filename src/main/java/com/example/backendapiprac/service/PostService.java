package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.PostRequestDto;
import com.example.backendapiprac.dto.PostResponseDto;
import com.example.backendapiprac.dto.UpdatePostRequestDto;
import com.example.backendapiprac.entity.Post;
import com.example.backendapiprac.entity.User;
import com.example.backendapiprac.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /* 게시글 수정 */
    @Transactional
    public ResponseEntity<ApiResponseDto> updatePost(Long post_id, UpdatePostRequestDto updatePostRequestDto, User user) {
        Post post = postRepository.findById(post_id).orElse(null);

        if(post == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "해당 게시글이 존재하지 않습니다."));
        }

        if(post.getUser().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글의 작성자가 아닙니다."));
        }

        String title = updatePostRequestDto.getTitle();
        post.setTitle(title);

        String contents = updatePostRequestDto.getContents();
        post.setContents(contents);

        PostResponseDto newPost = new PostResponseDto(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "게시글 수정 성공", newPost));
    }

    /* 게시글 삭제 */
    @Transactional
    public ResponseEntity<ApiResponseDto> deletePost(Long post_id, User user) {
        Post post = postRepository.findById(post_id).orElse(null);

        if(post == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "해당 게시글이 존재하지 않습니다."));
        }

        if(post.getUser().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글의 작성자가 아닙니다."));
        }

        postRepository.delete(post);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK.value(), "게시글 삭제 성공"));
    }
}
