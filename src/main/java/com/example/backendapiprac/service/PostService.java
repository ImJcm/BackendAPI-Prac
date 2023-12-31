package com.example.backendapiprac.service;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.PostRequestDto;
import com.example.backendapiprac.dto.UpdatePostRequestDto;
import com.example.backendapiprac.entity.User;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<ApiResponseDto> getPosts();

    /* 게시글 등록 */
    ResponseEntity<ApiResponseDto> registPost(PostRequestDto requestDto, User user);

    /* 게시글 조회 */
    ResponseEntity<ApiResponseDto> getPost(Long post_id);

    /* 게시글 수정 */
    ResponseEntity<ApiResponseDto> updatePost(Long post_id, UpdatePostRequestDto updatePostRequestDto, User user);

    /* 게시글 삭제 */
    ResponseEntity<ApiResponseDto> deletePost(Long post_id, User user);

    /* 게시글 제목 keyword 검색 - QueryDSL */
    ResponseEntity<ApiResponseDto> searchPost(String keyword);

    /* 게시글 keyword 검색 - QueryDSL + Paging */
    ResponseEntity<ApiResponseDto> searchPageablePost(String keyword, Integer page, Integer size, String sortBy, Boolean isAsc);


}
