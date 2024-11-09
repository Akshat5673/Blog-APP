package com.blogApp.blog.Repositories;

import com.blogApp.blog.Dtos.CommentDto;
import com.blogApp.blog.Entities.Comment;
import com.blogApp.blog.Entities.Post;
import com.blogApp.blog.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

    Page<Comment> findAllByUser(User user, Pageable pageable);
}
