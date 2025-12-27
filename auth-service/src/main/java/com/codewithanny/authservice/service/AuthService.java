package com.codewithanny.authservice.service;

import com.codewithanny.authservice.dto.LoginRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService,  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // password request -> password -> encoded -> $dsjhfuisdhfb#$#48
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token = userService.
                findByEmail(loginRequestDTO.getEmail()).
                filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u-> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }
}
