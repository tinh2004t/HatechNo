package com.hatechno.controller;

import com.hatechno.model.User;
import com.hatechno.repository.UserRepository;
import com.hatechno.security.JwtTokenProvider; // Import lớp JwtTokenProvider
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider; // Thêm JwtTokenProvider

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, 
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa mật khẩu
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Tạo JWT Token
            String token = jwtTokenProvider.generateToken(user.getUsername());

            // Trả về Token thay vì chỉ trả về thông báo
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid username or password!");
        }
    }
}
