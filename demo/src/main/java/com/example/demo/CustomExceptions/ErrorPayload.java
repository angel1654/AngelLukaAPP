package com.example.demo.CustomExceptions;

// A record automatically creates the constructor and getters for us behind the scenes!
public record ErrorPayload(String errorCode, String message) {
}