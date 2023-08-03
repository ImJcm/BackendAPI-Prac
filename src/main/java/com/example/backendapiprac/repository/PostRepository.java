package com.example.backendapiprac.repository;

import com.example.backendapiprac.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryQuery{
    List<Post> findAllByOrderByCreatedAtDesc();
}
