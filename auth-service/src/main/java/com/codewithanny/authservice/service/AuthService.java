package com.codewithanny.authservice.service;

import com.codewithanny.authservice.dto.LoginRequestDTO;
import com.codewithanny.authservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService,  PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // password request -> password -> encoded -> $dsjhdhfb#$#48
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token = userService.
                findByEmail(loginRequestDTO.getEmail()).
                filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u-> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }
}
