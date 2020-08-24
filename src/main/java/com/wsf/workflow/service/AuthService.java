package com.wsf.workflow.service;

import com.wsf.workflow.dto.CommonUtils;
import com.wsf.workflow.dto.replica.UserDTO;
import com.wsf.workflow.dto.request.RefreshTokenRequest;
import com.wsf.workflow.dto.response.AuthResponse;
import com.wsf.workflow.dto.response.LoginResponse;
import com.wsf.workflow.dto.response.RegistrationResponse;
import com.wsf.workflow.entity.EmployeeDetails;
import com.wsf.workflow.entity.User;
import com.wsf.workflow.entity.UserInformation;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.EmployeeDetailsRepository;
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
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    private final EmployeeDetailsRepository empDetailsRepository;

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


    public UserDTO save(UserDTO userDTO) {
        if ((CommonUtils.roles.get("USER")).equalsIgnoreCase(userDTO.getRole())) {
            return createUSER(userDTO);
        } else {
            return createEMP(userDTO);
        }
    }

    @Transactional
    public UserDTO createUSER(UserDTO userDTO) {
        UserInformation userInformation = mapToEntityUINFO(userDTO);
        User user = mapToEntityUSER(userDTO);
        try {
//            save user
            userRepository.save(user);
//            assign user to user details and user dto
            userDTO.setId(user.getId());
            userInformation.setUser(user);
//            save user details
            userInformationRepository.save(userInformation);
            userDTO.setRoleId(userInformation.getId());
        } catch (Exception e) {
            throw new CustomException("Exception occurred when creating user using email : " +
                    user.getEmail() + " and User Name : " + user.getUsername());
        }

        return userDTO;
    }

    @Transactional
    public UserDTO createEMP(UserDTO userDTO) {
        EmployeeDetails employeeDetails = mapToEntityEMP(userDTO);
        User user = mapToEntityUSER(userDTO);
        try {
//            save user
            userRepository.save(user);
//            assign user to employee details and user dto
            userDTO.setId(user.getId());
            employeeDetails.setUser(user);
//            save user details
            empDetailsRepository.save(employeeDetails);
            userDTO.setRoleId(employeeDetails.getId());
        } catch (Exception e) {
            throw new CustomException("Exception occurred when creating employee using email : " +
                    user.getEmail() + " and User Name : " + user.getUsername());
        }
        return userDTO;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser() {
        return userInformationRepository.findAll()
                .stream()
                .map(this::mapToDtoUINFO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllemp() {
        return empDetailsRepository.findAll()
                .stream()
                .map(this::mapToDtoEMP)
                .collect(toList());
    }

    //    get user dto from user information / employeedetails of provided user id
    @Transactional(readOnly = true)
    public UserDTO getUserByID(Long id) {
        User user = userRepository.getOne(id);
        if ((CommonUtils.roles.get("USER")).equalsIgnoreCase(user.getRole())) {
            return mapToDtoUINFO(userInformationRepository.getUserInformationByUser(user));
        } else {
            return mapToDtoEMP(empDetailsRepository.getEmployeeDetailsByUser(user));
        }
    }

    //    maps userDTO object to user entity
    public User mapToEntityUSER(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setOnCreate(1L);
        return user;
    }

    //    maps userDTO object to user information entity
    public UserInformation mapToEntityUINFO(UserDTO userDTO) {
        UserInformation userInformation = new UserInformation();
        userInformation.setMobile(userDTO.getMobile());
        userInformation.setFullName(userDTO.getFullName());
        userInformation.setAddress(userDTO.getAddress());
        userInformation.setOnCreate(1L);
        return userInformation;
    }

    public EmployeeDetails mapToEntityEMP(UserDTO userDTO) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setOnCreate(1L);
        employeeDetails.setFullName(userDTO.getFullName());
        employeeDetails.setDesignation(userDTO.getDesignation());
        employeeDetails.setMobile(userDTO.getMobile());
        employeeDetails.setAddress(userDTO.getAddress());
        return employeeDetails;
    }

    public UserDTO mapToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    //    generates full dto object including user from user information entity object
    public UserDTO mapToDtoUINFO(UserInformation userInformation) {
        UserDTO userDTO = mapToDto(userInformation.getUser());
        userDTO.setRoleId(userInformation.getId());
        userDTO.setFullName(userInformation.getFullName());
        userDTO.setAddress(userInformation.getAddress());
        userDTO.setMobile(userInformation.getMobile());
        return userDTO;
    }

    //    generates full dto object including user from employee information entity object
    public UserDTO mapToDtoEMP(EmployeeDetails employeeDetails) {
        UserDTO userDTO = mapToDto(employeeDetails.getUser());
        userDTO.setRoleId(employeeDetails.getId());
        userDTO.setFullName(employeeDetails.getFullName());
        userDTO.setAddress(employeeDetails.getAddress());
        userDTO.setMobile(employeeDetails.getMobile());
        userDTO.setDesignation(employeeDetails.getDesignation());
        return userDTO;
    }
}
