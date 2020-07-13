package com.wsf.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private String username;
    private String password;
    private String email;
    private String fullName;
    private String address;
    private String mobile;
}
