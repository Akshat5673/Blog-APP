package com.blogApp.blog.Dtos;



import com.blogApp.blog.Entities.Comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Integer id;
    @NotEmpty
    @Size(min = 4, message = "Title cannot have less than 4 characters !")
    private String postTitle;
    @NotEmpty
    @Size(max = 1000, message = "Content cannot be more than 1000 words !")
    private String postContent;

    private String imageName;
    private Date postDate;
    private String categoryTitle;
    private String userName;
    private List<CommentDto> comments = new ArrayList<>();

}
