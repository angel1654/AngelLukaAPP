package com.example.demo;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user with that username already exists!!!");
        }
        User user = new User(username, passwordEncoder.encode(password));
        return userRepository.save(user);

    }

    public ResponseEntity<Map<String,String>> userLogin(String username, String password)
    {
        User user = userRepository.findByUsername(username);
        boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if(isMatch)
        {
            Map<String, String> response = new HashMap<>();
            response.put("token",jwtUtil.generateToken(username));
            return ResponseEntity.ok(response);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"the username or password you entered is wrong");
    }
}
