package com.blogApp.blog.Controllers;

import com.blogApp.blog.Dtos.UserDto;

import com.blogApp.blog.Payloads.ApiResponse;
import com.blogApp.blog.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN_USER')")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<UserDto> createAdmin(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }

    @PutMapping("update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
        UserDto updatedUser = userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }


    @GetMapping("retrieve/{userId}")
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<UserDto> retrieveUser(@PathVariable Integer userId){
        UserDto fetchedUser = userService.getUserById(userId);
        return ResponseEntity.ok(fetchedUser);
    }


    @GetMapping("list/")
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<List<UserDto>> retrieveUserList(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
