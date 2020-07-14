package com.wsf.workflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String authToken;
    private String refreshToken;
    private  String username;
    private  String role;
    private Instant expiresAt;
}
