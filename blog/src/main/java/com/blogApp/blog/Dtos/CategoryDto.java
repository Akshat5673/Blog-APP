package com.blogApp.blog.Dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private Integer id;
    @NotEmpty
    @Size(min = 4, message = "Title cannot have less than 4 characters !")
    private String categoryTitle;
    @NotEmpty
    @Size(max = 300, message = "Description cannot be more than 300 words !")
    private String categoryDescription;
}
