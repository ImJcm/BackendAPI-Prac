package com.example.backendapiprac.repository;

import com.example.backendapiprac.entity.Post;
import com.example.backendapiprac.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    /* QueryDSL 사용하여 게시글 제목 중 keyword 검색 */
    @Override
    public List<Post> search(String keyword) {
        QPost post = QPost.post;

       var query = jpaQueryFactory.select(post)
                .from(post)
                .where(post.title.contains(keyword))
                .fetch();

       
       return query;
    }
}
