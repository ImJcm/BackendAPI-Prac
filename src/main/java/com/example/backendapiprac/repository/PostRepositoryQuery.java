package com.example.backendapiprac.repository;

import com.example.backendapiprac.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryQuery {
    List<Post> search(String keyword);

    List<Post> searchPageable(String keyword, Pageable pageable);
}