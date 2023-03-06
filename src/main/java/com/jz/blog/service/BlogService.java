package com.jz.blog.service;

import com.jz.blog.domain.Blog;
import com.jz.blog.repository.BlogRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Blog}.
 */
@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * Save a blog.
     *
     * @param blog the entity to save.
     * @return the persisted entity.
     */
    public Blog save(Blog blog) {
        log.debug("Request to save Blog : {}", blog);
        return blogRepository.save(blog);
    }

    /**
     * Update a blog.
     *
     * @param blog the entity to save.
     * @return the persisted entity.
     */
    public Blog update(Blog blog) {
        log.debug("Request to update Blog : {}", blog);
        return blogRepository.save(blog);
    }

    /**
     * Partially update a blog.
     *
     * @param blog the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Blog> partialUpdate(Blog blog) {
        log.debug("Request to partially update Blog : {}", blog);

        return blogRepository
            .findById(blog.getId())
            .map(existingBlog -> {
                if (blog.getTitle() != null) {
                    existingBlog.setTitle(blog.getTitle());
                }
                if (blog.getContent() != null) {
                    existingBlog.setContent(blog.getContent());
                }

                return existingBlog;
            })
            .map(blogRepository::save);
    }

    /**
     * Get all the blogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Blog> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll(pageable);
    }

    /**
     * Get all the blogs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Blog> findAllWithEagerRelationships(Pageable pageable) {
        return blogRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one blog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Blog> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        return blogRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the blog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
    }
}
