package com.wsf.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String username;
    private String password;
    private String email;
    private Long empID; // employeeDetails entity id
    private String fullName;
    private String address;
    private String mobile;
    private String designation;
    private byte[] picture;
    private String ppMimeType;
    private byte[] signature;
    private String signatureMimeType;
    private String role = "EMPLOYEE";
}
