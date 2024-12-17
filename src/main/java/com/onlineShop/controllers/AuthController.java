package com.onlineShop.controllers;

import com.onlineShop.dto.auth.LoginRequest;
import com.onlineShop.dto.auth.LoginResponse;
import com.onlineShop.dto.auth.RegisterRequest;
import com.onlineShop.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<LoginResponse> login(@Valid @RequestBody  LoginRequest request) {
        return authService.login(request);
    }
}
