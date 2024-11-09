package com.blogApp.blog.Exceptions;

import com.blogApp.blog.Payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException (ResourceNotFoundException e){
        String message= e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgsNotValidException(MethodArgumentNotValidException e){
        Map<String,String> errorResponse = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errorResponse.put(fieldName,message);
        });
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> alreadyExistsException (AlreadyExistsException e){
        String message= e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadCredentialsException> badCredentialsException(BadCredentialsException e) {
        String message = e.getMessage();
        BadCredentialsException badCredentialsException = new BadCredentialsException(message,true);
        return new ResponseEntity<>(badCredentialsException,HttpStatus.BAD_REQUEST);
    }

}
