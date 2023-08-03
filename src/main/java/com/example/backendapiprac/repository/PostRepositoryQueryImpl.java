package com.example.backendapiprac.repository;

import com.example.backendapiprac.entity.Post;
import com.example.backendapiprac.entity.QPost;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

    /* QueryDSL + Pageable */
    @Override
    public List<Post> searchPageable(String keyword, Pageable pageable) {
        QPost post = QPost.post;

        Long page = pageable.getOffset();
        Integer size = pageable.getPageSize();

        /*for(Sort.Order order : pageable.getSort()) {
            String property = order.getProperty();
        }*/
        String property = pageable.getSort().stream().collect(Collectors.toList()).get(0).getProperty();
        Sort.Direction direction = pageable.getSort().stream().collect(Collectors.toList()).get(0).getDirection();
        //OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.ASC, post.title);
        OrderSpecifier<?> orderSpecifier = null;

        if(direction.toString().equals("ASC")) {
            if(property.equals("title")) {
                orderSpecifier = new OrderSpecifier<>(Order.ASC, post.title);
            }
        } else {
            // DESC
            if(property.equals("title")) {
                orderSpecifier = new OrderSpecifier<>(Order.DESC, post.title);
            }
        }

        return jpaQueryFactory.selectFrom(post)
                .offset(page)
                .limit(size)
                .where(post.title.contains(keyword))
                .orderBy(orderSpecifier)
                .fetch();
    }
}
