package com.example.demo.CustomExceptions;

import com.example.demo.CustomExceptions.ErrorPayload;
import com.example.demo.CustomExceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 1. This tells Spring to wrap this net around ALL your controllers
public class GlobalExceptionHandler {

    // 2. This tells Spring: "If you see a UserAlreadyExistsException, route it HERE"
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorPayload> handleUserExists(UserAlreadyExistsException ex) {

        // 3. Put the message from the flare into our custom JSON box
        ErrorPayload payload = new ErrorPayload("USER_EXISTS", ex.getMessage());

        // 4. Send it to the frontend with a 409 (Conflict) HTTP status
        return new ResponseEntity<>(payload, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<ErrorPayload> handleWrongCredentials(WrongCredentialsException wc)
    {
        ErrorPayload payload = new ErrorPayload("WRONG_CREDENTIALS", wc.getMessage());
        return new ResponseEntity<>(payload,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BlankTaskTitleException.class)
    public ResponseEntity<ErrorPayload> handleBlankTaskTitle(BlankTaskTitleException btt)
    {
        ErrorPayload payload = new ErrorPayload("BLANK_TASK_TITLE", btt.getMessage());
        return new ResponseEntity<>(payload,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BlankTaskDescriptionException.class)
    public ResponseEntity<ErrorPayload> handleBlankTaskDescription(BlankTaskDescriptionException btd)
    {
        ErrorPayload payload =  new ErrorPayload("BLANK_TASK_DESCRIPTION",btd.getMessage());
        return new ResponseEntity<>(payload,HttpStatus.CONFLICT);
    }
}