package com.spring.blog.controller;

import com.spring.blog.payload.JwtAuthResponse;
import com.spring.blog.payload.LoginDto;
import com.spring.blog.payload.RegisterDto;
import com.spring.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login rest API endpoint
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);

    }

    // Register rest API endpoint
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> Register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
