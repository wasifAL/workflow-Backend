package com.wsf.workflow.service;

import com.wsf.workflow.dto.RegistrationDTO;
import com.wsf.workflow.entity.User;
import com.wsf.workflow.entity.UserInformation;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.UserInformationRepository;
import com.wsf.workflow.repository.UserRepository;
import com.wsf.workflow.dto.Commons;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;

    @Transactional
    public void register(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setOnCreate(null);
        user.setRole(Commons.roles.get("USER"));

        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(registrationDTO.getAddress());
        userInformation.setFullName(registrationDTO.getFullName());
        userInformation.setMobile(registrationDTO.getMobile());

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
}