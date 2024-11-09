package com.blogApp.blog.Exceptions;

public class BadCredentialsException extends RuntimeException{

    public BadCredentialsException(String message){
        super(message);
    }

    public BadCredentialsException(String message, Boolean bool){
        super();
    }

}
