package com.blogApp.blog.Services;

import com.blogApp.blog.Dtos.CommentDto;
import com.blogApp.blog.Payloads.PageResponse;


public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    void deleteComment(Integer commentId);

    PageResponse<CommentDto> getCommentsByUser(Integer userId, Integer pageNumber, Integer pageSize,
                                   String sortBy, String sortOrder);
    CommentDto updateComment(CommentDto commentDto, Integer userId);


}
