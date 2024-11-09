package com.blogApp.blog.Controllers;

import com.blogApp.blog.Dtos.CommentDto;
import com.blogApp.blog.Payloads.ApiResponse;
import com.blogApp.blog.Payloads.PageResponse;
import com.blogApp.blog.Services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.blogApp.blog.Utils.Constants.*;
import static com.blogApp.blog.Utils.Constants.SORT_ORDER;

@RestController
@RequestMapping("/api/comments")
@PreAuthorize("hasAnyRole('ADMIN_USER','NORMAL_USER')")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/user/{userId}/")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<PageResponse<CommentDto>> retrieveCommentsByUser(@PathVariable Integer userId
            , @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER,required = false) Integer pageNumber
            , @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE,required = false) Integer pageSize
            , @RequestParam(value = "sortBy", defaultValue = SORT_BY,required = false) String sortBy
            , @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER,required = false) String sortOrder){

        PageResponse<CommentDto> comments = commentService
                .getCommentsByUser(userId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/user/{userId}/post/{postId}/")
    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE','USER_CREATE')")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto
            , @PathVariable Integer postId, @PathVariable Integer userId){
        CommentDto newComment = commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{commentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE','USER_DELETE')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("Comment Deleted Successfully",true));
    }
}
