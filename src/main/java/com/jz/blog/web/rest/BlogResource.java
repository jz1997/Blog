package com.jz.blog.web.rest;

import com.jz.blog.domain.Blog;
import com.jz.blog.domain.User;
import com.jz.blog.repository.BlogRepository;
import com.jz.blog.repository.UserRepository;
import com.jz.blog.security.SecurityUtils;
import com.jz.blog.service.BlogQueryService;
import com.jz.blog.service.BlogService;
import com.jz.blog.service.criteria.BlogCriteria;
import com.jz.blog.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jz.blog.domain.Blog}.
 */
@RestController
@RequestMapping("/api")
public class BlogResource {

    public static class BlogResourceException extends RuntimeException {
        public BlogResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private static final String ENTITY_NAME = "blog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogService blogService;

    private final BlogRepository blogRepository;

    private final BlogQueryService blogQueryService;
    private final UserRepository userRepository;

    public BlogResource(BlogService blogService, BlogRepository blogRepository, BlogQueryService blogQueryService,
                        UserRepository userRepository) {
        this.blogService = blogService;
        this.blogRepository = blogRepository;
        this.blogQueryService = blogQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /blogs} : Create a new blog.
     *
     * @param blog the blog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blog, or with status {@code 400 (Bad Request)} if the blog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blogs")
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", blog);
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new BlogResource.BlogResourceException("Current user login not found"));

        if (blog.getId() != null) {
            throw new BadRequestAlertException("A new blog cannot already have an ID", ENTITY_NAME, "idexists");
        }

        User author = userRepository.findOneByLogin(userLogin)
            .orElseThrow(() -> new BlogResourceException("User could not be found"));

        blog.setAuthor(author);
        Blog result = blogService.save(blog);
        return ResponseEntity
            .created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blogs/:id} : Updates an existing blog.
     *
     * @param id the id of the blog to save.
     * @param blog the blog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blog,
     * or with status {@code 400 (Bad Request)} if the blog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Blog blog)
        throws URISyntaxException {
        log.debug("REST request to update Blog : {}, {}", id, blog);
        if (blog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Blog result = blogService.update(blog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blogs/:id} : Partial updates given fields of an existing blog, field will ignore if it is null
     *
     * @param id the id of the blog to save.
     * @param blog the blog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blog,
     * or with status {@code 400 (Bad Request)} if the blog is not valid,
     * or with status {@code 404 (Not Found)} if the blog is not found,
     * or with status {@code 500 (Internal Server Error)} if the blog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blogs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Blog> partialUpdateBlog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Blog blog
    ) throws URISyntaxException {
        log.debug("REST request to partial update Blog partially : {}, {}", id, blog);
        if (blog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Blog> result = blogService.partialUpdate(blog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blog.getId().toString())
        );
    }

    /**
     * {@code GET  /blogs} : get all the blogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogs in body.
     */
    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getAllBlogs(BlogCriteria criteria, @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get Blogs by criteria: {}", criteria);
        Page<Blog> page = blogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blogs/count} : count all the blogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/blogs/count")
    public ResponseEntity<Long> countBlogs(BlogCriteria criteria) {
        log.debug("REST request to count Blogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(blogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blogs/:id} : get the "id" blog.
     *
     * @param id the id of the blog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable Long id) {
        log.debug("REST request to get Blog : {}", id);
        Optional<Blog> blog = blogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blog);
    }

    /**
     * {@code DELETE  /blogs/:id} : delete the "id" blog.
     *
     * @param id the id of the blog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.debug("REST request to delete Blog : {}", id);
        blogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
