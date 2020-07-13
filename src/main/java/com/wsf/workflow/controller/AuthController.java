package com.wsf.workflow.controller;

import com.wsf.workflow.dto.AuthResponse;
import com.wsf.workflow.dto.LoginResponse;
import com.wsf.workflow.dto.RegistrationResponse;
import com.wsf.workflow.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationResponse registrationResponse) {
        authService.register(registrationResponse);
        return new ResponseEntity<>("User registration Successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginResponse loginResponse){
        return authService.login(loginResponse);
    }
}
