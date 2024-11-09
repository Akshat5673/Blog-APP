package com.blogApp.blog.Repositories;

import com.blogApp.blog.Entities.Category;
import com.blogApp.blog.Entities.Post;
import com.blogApp.blog.Entities.User;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    boolean existsByPostTitle(String postTitle);

    Page<Post> findByPostTitleContaining(String postTitle, Pageable pageable);
}
