package com.example.demo.CustomExceptions;

public class BlankTaskDescriptionException extends RuntimeException{
    public BlankTaskDescriptionException(String message)
    {
        super(message);
    }
}
