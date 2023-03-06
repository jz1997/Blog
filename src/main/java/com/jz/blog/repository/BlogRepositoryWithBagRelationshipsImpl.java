package com.jz.blog.repository;

import com.jz.blog.domain.Blog;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BlogRepositoryWithBagRelationshipsImpl implements BlogRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Blog> fetchBagRelationships(Optional<Blog> blog) {
        return blog.map(this::fetchTags);
    }

    @Override
    public Page<Blog> fetchBagRelationships(Page<Blog> blogs) {
        return new PageImpl<>(fetchBagRelationships(blogs.getContent()), blogs.getPageable(), blogs.getTotalElements());
    }

    @Override
    public List<Blog> fetchBagRelationships(List<Blog> blogs) {
        return Optional.of(blogs).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Blog fetchTags(Blog result) {
        return entityManager
            .createQuery("select blog from Blog blog left join fetch blog.tags where blog is :blog", Blog.class)
            .setParameter("blog", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Blog> fetchTags(List<Blog> blogs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, blogs.size()).forEach(index -> order.put(blogs.get(index).getId(), index));
        List<Blog> result = entityManager
            .createQuery("select distinct blog from Blog blog left join fetch blog.tags where blog in :blogs", Blog.class)
            .setParameter("blogs", blogs)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
