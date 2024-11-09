package com.blogApp.blog.Services.Impls;

import com.blogApp.blog.Adapters.GenericEntityDtoAdapter;
import com.blogApp.blog.Dtos.CommentDto;
import com.blogApp.blog.Entities.Comment;
import com.blogApp.blog.Entities.Post;
import com.blogApp.blog.Entities.User;
import com.blogApp.blog.Exceptions.ResourceNotFoundException;
import com.blogApp.blog.Payloads.PageResponse;
import com.blogApp.blog.Repositories.CommentRepo;
import com.blogApp.blog.Repositories.PostRepo;
import com.blogApp.blog.Repositories.UserRepo;
import com.blogApp.blog.Services.CommentService;
import com.blogApp.blog.Utils.PageResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;

    private final UserRepo userRepo;

    @Autowired
    public CommentServiceImpl(PostRepo postRepo, CommentRepo commentRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
    }


    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {

        User user= userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = GenericEntityDtoAdapter.toEntityObject(commentDto,Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment =  commentRepo.save(comment);
        CommentDto createdComment = GenericEntityDtoAdapter.toDtoObject(savedComment, CommentDto.class);
        createdComment.setUserName(user.getUsername());
        return createdComment;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        commentRepo.delete(comment);
    }

    @Override
    public PageResponse<CommentDto> getCommentsByUser(Integer userId, Integer pageNumber, Integer pageSize,
                                                      String sortBy, String sortOrder) {
        Pageable pageable = PageResponseUtil.createPageable(pageNumber, pageSize, sortBy, sortOrder);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Page<Comment> commentPage = commentRepo.findAllByUser(user, pageable);
        return PageResponseUtil.createPageResponse(commentPage,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer userId) {
        return null;
    }
}
