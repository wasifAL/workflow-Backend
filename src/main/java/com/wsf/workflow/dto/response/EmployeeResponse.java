package com.wsf.workflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String username;
    private String password;
    private String email;
    private Long empID; // employeeDetails entity id
    private String fullname;
    private String address;
    private String mobile;
    private String designation;
    private MultipartFile picture;
    private String ppMimeType;
    private MultipartFile signature;
    private String signatureMimeType;
    private String role = "EMPLOYEE";
}
