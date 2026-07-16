package com.example.demo;

import com.example.demo.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Inject your JwtUtil so you can verify the tokens
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String username = jwtUtil.extractUsername(token);

                // If token is valid and user isn't already logged in this request
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Create the simplest possible authentication object
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                    // Save the user in the context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                else
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"invalid token");
            } catch (Exception e) {
                // Silently ignore invalid/expired tokens here.
                // Spring Security will automatically block the request before it reaches the controller.
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"invalid token");
            }
        }

        filterChain.doFilter(request, response);
    }
}