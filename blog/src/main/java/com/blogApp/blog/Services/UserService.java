package com.blogApp.blog.Services;

import com.blogApp.blog.Dtos.UserDto;


import java.util.List;


public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto createUser( UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);


}
