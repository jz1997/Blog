package com.jz.blog.repository;

import com.jz.blog.domain.Blog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Blog entity.
 *
 * When extending this class, extend BlogRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface BlogRepository extends BlogRepositoryWithBagRelationships, JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select blog from Blog blog where blog.author.login = ?#{principal.username}")
    List<Blog> findByAuthorIsCurrentUser();

    default Optional<Blog> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Blog> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Blog> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct blog from Blog blog left join fetch blog.author left join fetch blog.category",
        countQuery = "select count(distinct blog) from Blog blog"
    )
    Page<Blog> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct blog from Blog blog left join fetch blog.author left join fetch blog.category")
    List<Blog> findAllWithToOneRelationships();

    @Query("select blog from Blog blog left join fetch blog.author left join fetch blog.category where blog.id =:id")
    Optional<Blog> findOneWithToOneRelationships(@Param("id") Long id);
}
