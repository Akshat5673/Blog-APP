package com.blogApp.blog.Dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Integer id;
    private String userName;
    @NotEmpty
    @Size(max = 300, message = "Comment cannot be more than 300 words !")
    private String commentContent;
}
