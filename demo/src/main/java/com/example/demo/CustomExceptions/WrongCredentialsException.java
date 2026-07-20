package com.example.demo.CustomExceptions;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException(String message)
    {
        super(message);
    }
}
