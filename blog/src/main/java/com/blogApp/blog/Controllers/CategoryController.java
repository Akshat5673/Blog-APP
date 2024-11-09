package com.blogApp.blog.Controllers;

import com.blogApp.blog.Dtos.CategoryDto;
import com.blogApp.blog.Payloads.ApiResponse;
import com.blogApp.blog.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("hasRole('ADMIN_USER')")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("create-admin/")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable Integer categoryId){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);

    }

    @GetMapping("retrieve/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<CategoryDto> retrieveCategory(@PathVariable Integer categoryId){
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("list/")
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<List<CategoryDto>> getCategoryList(){
        return ResponseEntity.ok(categoryService.listCategory());
    }

}
