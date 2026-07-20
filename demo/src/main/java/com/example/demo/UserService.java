package com.example.demo;

import com.example.demo.CustomExceptions.UserAlreadyExistsException;
import com.example.demo.CustomExceptions.WrongCredentialsException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtUtil = jwtUtil;
    }

    public User userRegister(String username, String password)
    {
        if(userRepository.existsByUsername(username))
        {
            throw new UserAlreadyExistsException("username already exists!");
        }
        User user = new User(username, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public ResponseEntity<Map<String,String>> userLogin(String username, String password)
    {
        if(username.isBlank())
        {
            throw new WrongCredentialsException("you must enter a username");
        }
        else if(password.isBlank())
        {
            throw new WrongCredentialsException("you must enter a password");
        }
        User user = userRepository.findByUsername(username);
        if(user==null)
            throw new WrongCredentialsException("wrong username or password!");
        boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if(isMatch)
        {
            Map<String, String> response = new HashMap<>();
            response.put("token",jwtUtil.generateToken(username));
            return ResponseEntity.ok(response);
        }
        throw new WrongCredentialsException("wrong username or password!");
    }
}
