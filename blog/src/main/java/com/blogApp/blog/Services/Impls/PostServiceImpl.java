package com.blogApp.blog.Services.Impls;

import com.blogApp.blog.Adapters.GenericEntityDtoAdapter;
import com.blogApp.blog.Dtos.PostDto;
import com.blogApp.blog.Entities.Category;
import com.blogApp.blog.Entities.Post;
import com.blogApp.blog.Entities.User;
import com.blogApp.blog.Exceptions.AlreadyExistsException;
import com.blogApp.blog.Exceptions.ResourceNotFoundException;
import com.blogApp.blog.Payloads.PageResponse;
import com.blogApp.blog.Repositories.CategoryRepo;
import com.blogApp.blog.Repositories.PostRepo;
import com.blogApp.blog.Repositories.UserRepo;
import com.blogApp.blog.Services.PostService;
import com.blogApp.blog.Utils.PageResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;


import static com.blogApp.blog.Utils.Constants.CATEGORY;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {

        if (postRepo.existsByPostTitle(postDto.getPostTitle())){
            throw new AlreadyExistsException("Post","Title", postDto.getPostTitle());
        }

        User user= userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id", userId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY,"Id",categoryId));

        Post post = GenericEntityDtoAdapter.toEntityObject(postDto, Post.class);
        post.setImageName("default.png");
        post.setPostDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepo.save(post);
        return GenericEntityDtoAdapter.toDtoObject(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);
        return GenericEntityDtoAdapter.toDtoObject(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        postRepo.delete(post);
    }

    @Override
    public PageResponse<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageable = PageResponseUtil.createPageable(pageNumber, pageSize, sortBy, sortOrder);
        Page<Post> postPage = postRepo.findAll(pageable);
        return PageResponseUtil.createPageResponse(postPage, PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        return GenericEntityDtoAdapter.toDtoObject(post, PostDto.class);
    }

    @Override
    public PageResponse<PostDto> getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize,
                                           String sortBy, String sortOrder) {

        Pageable pageable = PageResponseUtil.createPageable(pageNumber, pageSize, sortBy, sortOrder);
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        Page<Post> postPage = postRepo.findByCategory(category, pageable);
        return PageResponseUtil.createPageResponse(postPage, PostDto.class);
    }

    @Override
    public PageResponse<PostDto> getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize,
                                       String sortBy, String sortOrder) {

        Pageable pageable = PageResponseUtil.createPageable(pageNumber, pageSize, sortBy, sortOrder);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Page<Post> postPage = postRepo.findByUser(user, pageable);
        return PageResponseUtil.createPageResponse(postPage, PostDto.class);
    }

    @Override
    public PageResponse<PostDto> searchPosts(String keyword, Integer pageNumber, Integer pageSize,
                                    String sortBy, String sortOrder) {

        Pageable pageable = PageResponseUtil.createPageable(pageNumber, pageSize, sortBy, sortOrder);
        Page<Post> postPage = postRepo.findByPostTitleContaining(keyword, pageable);
        return PageResponseUtil.createPageResponse(postPage, PostDto.class);
    }


}
