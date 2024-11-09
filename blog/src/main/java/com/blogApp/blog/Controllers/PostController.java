package com.blogApp.blog.Controllers;


import com.blogApp.blog.Dtos.PostDto;
import com.blogApp.blog.Payloads.ApiResponse;
import com.blogApp.blog.Payloads.PageResponse;
import com.blogApp.blog.Services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.blogApp.blog.Utils.Constants.*;


@RestController
@RequestMapping("/api/post")
@PreAuthorize("hasAnyRole('ADMIN_USER','NORMAL_USER')")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/user/{userId}/category/{categoryId}/")
    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE','USER_CREATE')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto newPost = postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("retrieve/{postId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<PostDto> retrievePostById(@PathVariable Integer postId){
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }


    @GetMapping("/category/{categoryId}/")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<PageResponse<PostDto>> retrievePostByCategory(@PathVariable Integer categoryId
            , @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(value = "sortBy", defaultValue = SORT_BY,required = false) String sortBy
            , @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER,required = false) String sortOrder)
    {
        PageResponse<PostDto> posts = postService.getPostsByCategory(categoryId, pageNumber,
                                        pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/user/{userId}/")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<PageResponse<PostDto>> retrievePostByUser(@PathVariable Integer userId
            , @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(value = "sortBy", defaultValue = SORT_BY,required = false) String sortBy
            , @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER,required = false) String sortOrder)
    {
        PageResponse<PostDto> posts = postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(posts);
    }



    @GetMapping("list/")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<PageResponse<PostDto>> retrieveAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(value = "sortBy", defaultValue = SORT_BY,required = false) String sortBy
            , @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER,required = false) String sortOrder)
    {
        return ResponseEntity.ok(postService.getAllPosts(pageNumber, pageSize, sortBy,sortOrder));
    }


    @DeleteMapping("/delete/{postId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE','USER_DELETE')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted successfully !",true));
    }


    @PutMapping("/update/{postId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_UPDATE','USER_UPDATE')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto updatedPost = postService.updatePost(postDto,postId);
        return ResponseEntity.ok(updatedPost);
    }


    @GetMapping("/search/{keyword}")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<PageResponse<PostDto>> searchByTitle(@PathVariable String keyword
            , @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(value = "sortBy", defaultValue = SORT_BY,required = false) String sortBy
            , @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER,required = false) String sortOrder){
        return ResponseEntity.ok(postService.searchPosts(keyword,pageNumber, pageSize, sortBy,sortOrder));
    }

}
