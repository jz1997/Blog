package com.jz.blog.repository;

import com.jz.blog.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Optional<Comment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Comment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Comment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct comment from Comment comment left join fetch comment.blog",
        countQuery = "select count(distinct comment) from Comment comment"
    )
    Page<Comment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct comment from Comment comment left join fetch comment.blog")
    List<Comment> findAllWithToOneRelationships();

    @Query("select comment from Comment comment left join fetch comment.blog where comment.id =:id")
    Optional<Comment> findOneWithToOneRelationships(@Param("id") Long id);
}
