package com.example.demo.CustomExceptions;

public class BlankTaskTitleException extends RuntimeException{
    public BlankTaskTitleException(String message)
    {
        super(message);
    }
}
