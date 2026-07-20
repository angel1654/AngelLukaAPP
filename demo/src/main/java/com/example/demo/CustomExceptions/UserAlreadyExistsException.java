package com.example.demo.CustomExceptions;

// 1. Extending RuntimeException is what turns this normal class into a "throwable" error
public class UserAlreadyExistsException extends RuntimeException{
    // 2. This constructor allows you to pass your custom message when you throw it
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
