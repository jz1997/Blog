package com.jz.blog.repository;

import com.jz.blog.domain.Blog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BlogRepositoryWithBagRelationships {
    Optional<Blog> fetchBagRelationships(Optional<Blog> blog);

    List<Blog> fetchBagRelationships(List<Blog> blogs);

    Page<Blog> fetchBagRelationships(Page<Blog> blogs);
}
