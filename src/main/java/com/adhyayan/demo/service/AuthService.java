package com.adhyayan.demo.service;

import com.adhyayan.demo.dto.AuthResponse;
import com.adhyayan.demo.dto.LoginRequest;
import com.adhyayan.demo.dto.RegisterRequest;
import com.adhyayan.demo.exception.BadRequestException;
import com.adhyayan.demo.model.User;
import com.adhyayan.demo.repository.UserRepository;
import com.adhyayan.demo.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Username already exists");
        }
        if(request.getRole()==null){
            throw new BadRequestException("Role should not be null");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request){
        var authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        authenticationManager.authenticate(authToken);
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}
