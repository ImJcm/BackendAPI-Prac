package com.example.backendapiprac.repository;

import com.example.backendapiprac.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepositoryQuery {
    List<Post> search(String keyword);
}
