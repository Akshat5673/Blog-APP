package com.blogApp.blog.Services.Impls;

import com.blogApp.blog.Adapters.GenericEntityDtoAdapter;
import com.blogApp.blog.Dtos.UserDto;
import com.blogApp.blog.Entities.User;
import com.blogApp.blog.Enums.Role;
import com.blogApp.blog.Exceptions.AlreadyExistsException;
import com.blogApp.blog.Exceptions.ResourceNotFoundException;
import com.blogApp.blog.Repositories.UserRepo;
import com.blogApp.blog.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = GenericEntityDtoAdapter.toEntityObject(userDto,User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.NORMAL_USER);

            User newUser = userRepo.save(user);

        return GenericEntityDtoAdapter.toDtoObject(newUser,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        if(userRepo.existsByName(userDto.getName().trim())){
            throw new AlreadyExistsException("User","Name", userDto.getName());
        }
        if (userRepo.existsByUserEmail(userDto.getUserEmail())){
            throw new AlreadyExistsException("User","Email Id", userDto.getUserEmail());
        }

        User user = GenericEntityDtoAdapter.toEntityObject(userDto,User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ADMIN_USER);

        User newAdmin = userRepo.save(user);
        return GenericEntityDtoAdapter.toDtoObject(newAdmin,UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user= userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id", userId));

        if (!userDto.getName().equalsIgnoreCase(user.getName())
                && userRepo.existsByName(userDto.getName().trim())) { // Name exists in the system
                throw new AlreadyExistsException("User", "Name", userDto.getName());
            }

        // Check if the email is being updated and already exists in the system
        if (!userDto.getUserEmail().equalsIgnoreCase(user.getUserEmail())
                && userRepo.existsByUserEmail(userDto.getUserEmail())) { // Email exists in the system
                throw new AlreadyExistsException("User", "Email Id", userDto.getUserEmail());
            }

        user.setName(userDto.getName());
        user.setUserEmail(userDto.getUserEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);
        return GenericEntityDtoAdapter.toDtoObject(updatedUser,UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user= userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
        return GenericEntityDtoAdapter.toDtoObject(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return GenericEntityDtoAdapter.toDtoList(userList, UserDto.class);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user= userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
        userRepo.delete(user);

    }
}
