package com.wsf.workflow.service;

import com.wsf.workflow.dto.AuthResponse;
import com.wsf.workflow.dto.LoginResponse;
import com.wsf.workflow.dto.RegistrationResponse;
import com.wsf.workflow.entity.User;
import com.wsf.workflow.entity.UserInformation;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.UserInformationRepository;
import com.wsf.workflow.repository.UserRepository;
import com.wsf.workflow.dto.CommonUtils;
import com.wsf.workflow.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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

    public AuthResponse login(LoginResponse loginResponse) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginResponse.getUsername(),
                        loginResponse.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        String role = getRoleFromAuthentication(authentication.getAuthorities());
        return new AuthResponse(token, loginResponse.getUsername(),role);
    }
    private String getRoleFromAuthentication(Collection<? extends GrantedAuthority> grantedAuthorities) {
        StringBuilder role = new StringBuilder("");
        for (GrantedAuthority ga : grantedAuthorities) {
            role.append(ga.getAuthority());
            if (role != null) break;
        }
        return role.toString();
    }
}
