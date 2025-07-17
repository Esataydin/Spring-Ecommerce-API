package com.esataydin.auth.service;

import com.esataydin.auth.dto.AuthResponse;
import com.esataydin.auth.dto.LoginRequest;
import com.esataydin.auth.dto.RegisterRequest;
import com.esataydin.auth.exception.AuthException;
import com.esataydin.auth.security.JwtUtil;
import com.esataydin.user.entity.User;
import com.esataydin.user.entity.User.Role;
import com.esataydin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Generate token
        String token = jwtUtil.generateToken(savedUser.getEmail());
        
        return new AuthResponse(token, savedUser.getEmail(), savedUser.getRole().name());
    }
    
    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
            
            // Get user details
            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));
            
            // Generate token
            String token = jwtUtil.generateToken(user.getEmail());
            
            return new AuthResponse(token, user.getEmail(), user.getRole().name());
            
        } catch (Exception e) {
            throw new AuthException("Invalid credentials");
        }
    }
}
