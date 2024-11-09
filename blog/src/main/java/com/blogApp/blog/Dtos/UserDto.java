package com.blogApp.blog.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.*;



@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotEmpty
    @Size(min = 3, message = "Name must be of minimum 3 letters !")
    private String name;

    @NotEmpty
    @Email(message = "Email is Invalid !")
    private String userEmail;

    @NotEmpty
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 12, message = "Password must be 3-12 characters long !")
    private String password;


    @Size(max = 300, message = "About cannot be more than 300 words !")
    private String about;

    @JsonProperty( value = "role", access = JsonProperty.Access.READ_ONLY)
    private String role;

}
