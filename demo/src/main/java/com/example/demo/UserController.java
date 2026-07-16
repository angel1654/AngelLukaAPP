package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public User userRegister(@RequestBody User user)
    {
        return userService.userRegister(user.getUsername(), user.getPassword());
    }
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> userLogin(@RequestBody User user)
    {
        return userService.userLogin(user.getUsername(), user.getPassword());
    }

}
