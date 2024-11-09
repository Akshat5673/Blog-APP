package com.blogApp.blog.Services.Impls;

import com.blogApp.blog.Adapters.GenericEntityDtoAdapter;
import com.blogApp.blog.Dtos.CategoryDto;
import com.blogApp.blog.Entities.Category;
import com.blogApp.blog.Exceptions.AlreadyExistsException;
import com.blogApp.blog.Exceptions.ResourceNotFoundException;
import com.blogApp.blog.Repositories.CategoryRepo;
import com.blogApp.blog.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.blogApp.blog.Utils.Constants.CATEGORY;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        if(categoryRepo.existsByCategoryTitle(categoryDto.getCategoryTitle().trim())){
            throw new AlreadyExistsException(CATEGORY,"Title", categoryDto.getCategoryTitle());
        }

        Category category = GenericEntityDtoAdapter.toEntityObject(categoryDto,Category.class);
        categoryRepo.save(category);
        return GenericEntityDtoAdapter.toDtoObject(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY,"Id",categoryId));

        if(!(categoryDto.getCategoryTitle().equalsIgnoreCase(category.getCategoryTitle()))
                && categoryRepo.existsByCategoryTitle(category.getCategoryTitle())){
            throw new AlreadyExistsException(CATEGORY,"Title", categoryDto.getCategoryTitle());
        }

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = categoryRepo.save(category);
        return GenericEntityDtoAdapter.toDtoObject(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY,"Id",categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY,"Id",categoryId));
        return GenericEntityDtoAdapter.toDtoObject(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> listCategory() {
        List<Category> categories = categoryRepo.findAll();
        return GenericEntityDtoAdapter.toDtoList(categories,CategoryDto.class);
    }
}
