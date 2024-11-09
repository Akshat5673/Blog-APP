package com.blogApp.blog.Security;

import com.blogApp.blog.Exceptions.ResourceNotFoundException;
import com.blogApp.blog.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //loading user from db by username
        return userRepo.findByUserEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User","email :" +email,0L));
    }
}
