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
    private final EmployeeDetailsRepository empDetailsRepository;
    private final UserInformationRepository userDetailsRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = repository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found by username : " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getIsActive(), true, true,
                true, getAuthorities(user.getRole()));
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
            repository.save(user);
//            assign user to user details and user dto
            userDTO.setId(user.getId());
            userInformation.setUser(user);
//            save user details
            userDetailsRepository.save(userInformation);
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
            repository.save(user);
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
        return userDetailsRepository.findAll()
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
        User user = repository.getOne(id);
        if ((CommonUtils.roles.get("USER")).equalsIgnoreCase(user.getRole())) {
            return mapToDtoUINFO(userDetailsRepository.getUserInformationByUser(user));
        } else {
            return mapToDtoEMP(empDetailsRepository.getEmployeeDetailsByUser(user));
        }
    }

    //    maps userDTO object to user entity
    public User mapToEntityUSER(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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

    //    get user authority by role name
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
