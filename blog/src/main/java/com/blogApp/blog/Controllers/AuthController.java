package com.blogApp.blog.Controllers;

import com.blogApp.blog.Dtos.UserDto;
import com.blogApp.blog.Payloads.JwtAuthRequest;
import com.blogApp.blog.Payloads.JwtAuthResponse;
import com.blogApp.blog.Security.JwtTokenHelper;
import com.blogApp.blog.Services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenHelper jwtTokenHelper;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @Autowired
    public AuthController(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService
            , AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){

        authenticate(request.getEmail(),request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = JwtAuthResponse.builder()
                .token(token)
                .email(userDetails.getUsername()).build();

        return ResponseEntity.ok(response);
    }

    private void authenticate(String email, String password) {

        logger.info("Authenticating user with email: {}", email);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email,password);

        try {
            authenticationManager.authenticate(authenticationToken);
            logger.info("Authentication successful for user: {}", email);
        }
        catch (BadCredentialsException e){
            logger.error("Authentication failed for user: {}", email);
            throw new BadCredentialsException("Invalid Username or password !");
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
        UserDto newUser = userService.registerUser(userDto);
        return ResponseEntity.ok(newUser);
    }


}




