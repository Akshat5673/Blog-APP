package com.blogApp.blog.Repositories;

import com.blogApp.blog.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {

    boolean existsByCategoryTitle(String categoryTitle);
}
