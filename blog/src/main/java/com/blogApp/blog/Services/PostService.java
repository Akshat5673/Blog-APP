package com.blogApp.blog.Services;

import com.blogApp.blog.Dtos.PostDto;

import com.blogApp.blog.Payloads.PageResponse;


public interface PostService {

    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
    public PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    PageResponse<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    PostDto getPostById(Integer postId);
    PageResponse<PostDto> getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    PageResponse<PostDto> getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Search posts using a keyword
    PageResponse<PostDto> searchPosts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


}
