package com.wsf.workflow.service;

import com.wsf.workflow.dto.CommonUtils;
import com.wsf.workflow.dto.replica.StagesDTO;
import com.wsf.workflow.dto.replica.UserDTO;
import com.wsf.workflow.entity.EmployeeDetails;
import com.wsf.workflow.entity.Stages;
import com.wsf.workflow.entity.User;
import com.wsf.workflow.entity.UserInformation;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.EmployeeDetailsRepository;
import com.wsf.workflow.repository.UserInformationRepository;
import com.wsf.workflow.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(repository.findByUsername(username));
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found by username : " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getIsActive(), true, true,
                true, getAuthorities(user.getRole()));
    }

    //    get user authority by role name
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
