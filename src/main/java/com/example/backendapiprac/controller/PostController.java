package com.example.backendapiprac.controller;

import com.example.backendapiprac.dto.ApiResponseDto;
import com.example.backendapiprac.dto.PostRequestDto;
import com.example.backendapiprac.dto.UpdatePostRequestDto;
import com.example.backendapiprac.jwt.UserDetailsImpl;
import com.example.backendapiprac.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostServiceImpl postServiceImpl;

    /* 게시글 전체 조회 */
    @GetMapping("/posts")
    public ResponseEntity<ApiResponseDto> getPosts() {
        return postServiceImpl.getPosts();
    }

    /* 게시글 등록 */
    @PostMapping("/post")
    public ResponseEntity<ApiResponseDto> registPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postServiceImpl.registPost(requestDto, userDetails.getUser());
    }

    /* 게시글 조회 */
    @GetMapping("/post")
    public ResponseEntity<ApiResponseDto> getPost(@RequestParam Long post_id) {
        return postServiceImpl.getPost(post_id);
    }

    /* 게시글 수정 */
    @PutMapping("/post")
    public ResponseEntity<ApiResponseDto> updatePost(@RequestParam Long post_id, @RequestBody UpdatePostRequestDto updatePostRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postServiceImpl.updatePost(post_id, updatePostRequestDto, userDetails.getUser());
    }

    /* 게시글 삭제 */
    @DeleteMapping("/post")
    public ResponseEntity<ApiResponseDto> deletePost(@RequestParam Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postServiceImpl.deletePost(post_id, userDetails.getUser());
    }

    /* 게시글 keywork 검색 - QueryDSL*/
    // Format : /api/post/search?keyword="~~"
    @GetMapping("/post/search")
    public ResponseEntity<ApiResponseDto> searchPost(@RequestParam String keyword) {
        return postServiceImpl.searchPost(keyword);
    }

    /* 게시글 keywork 검색 - QueryDSL & Pageing*/
    // Format : /api/post/search?keyword="~~"&page=?&size=?&sortBy=?&isAsc=?
    @GetMapping("/post/searchPageable")
    public ResponseEntity<ApiResponseDto> searchPageablePost(
            @RequestParam String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        return postServiceImpl.searchPageablePost(keyword, page, size, sortBy, isAsc);
    }

}
