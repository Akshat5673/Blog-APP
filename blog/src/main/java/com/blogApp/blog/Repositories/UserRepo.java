package com.blogApp.blog.Repositories;

import com.blogApp.blog.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByName(String userName);

    boolean existsByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String email);
}
