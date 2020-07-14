package com.wsf.workflow.controller;

import com.wsf.workflow.dto.request.RefreshTokenRequest;
import com.wsf.workflow.dto.response.AuthResponse;
import com.wsf.workflow.dto.response.LoginResponse;
import com.wsf.workflow.dto.response.RegistrationResponse;
import com.wsf.workflow.service.AuthService;
import com.wsf.workflow.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationResponse registrationResponse) {
        authService.register(registrationResponse);
        return new ResponseEntity<>("User registration Successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginResponse loginResponse){
        return authService.login(loginResponse);
    }

    @PostMapping("refresh/token")
    public AuthResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }

}
