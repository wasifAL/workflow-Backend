package com.wsf.workflow.service;

import com.wsf.workflow.dto.request.RefreshTokenRequest;
import com.wsf.workflow.dto.response.AuthResponse;
import com.wsf.workflow.dto.response.LoginResponse;
import com.wsf.workflow.dto.response.RegistrationResponse;
import com.wsf.workflow.entity.User;
import com.wsf.workflow.entity.UserInformation;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.UserInformationRepository;
import com.wsf.workflow.repository.UserRepository;
import com.wsf.workflow.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void register(RegistrationResponse registrationResponse) {
        User user = new User();
        user.setEmail(registrationResponse.getEmail());
        user.setUsername(registrationResponse.getUsername());
        user.setPassword(passwordEncoder.encode(registrationResponse.getPassword()));
        user.setOnCreate(null);
        user.setRole("USER");

        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(registrationResponse.getAddress());
        userInformation.setFullName(registrationResponse.getFullName());
        userInformation.setMobile(registrationResponse.getMobile());

        try {
            userRepository.save(user);
            log.info("Created New User successfully.");
            userInformation.setUser(user);
            userInformation.setOnCreate(null);
            userInformationRepository.save(userInformation);
            log.info("Added User Information successfully.");
        } catch (Exception e) {
            throw new CustomException("Exception occurred when registering user using email : " +
                    user.getEmail() + " and User Name : " + user.getUsername());
        }
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthResponse login(LoginResponse loginResponse) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginResponse.getUsername(),
                        loginResponse.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        String role = getRoleFromAuthentication(authentication.getAuthorities());
        return AuthResponse.builder()
                .authToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginResponse.getUsername())
                .role(role)
                .build();
    }

    private String getRoleFromAuthentication(Collection<? extends GrantedAuthority> grantedAuthorities) {
        StringBuilder role = new StringBuilder("");
        for (GrantedAuthority ga : grantedAuthorities) {
            role.append(ga.getAuthority());
            if (role != null) break;
        }
        return role.toString();
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthResponse.builder()
                .authToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
