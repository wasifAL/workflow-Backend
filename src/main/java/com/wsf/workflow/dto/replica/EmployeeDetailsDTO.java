package com.wsf.workflow.dto.replica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsDTO {
    private String fullName;
    private String address;
    private String mobile;
    private String designation;
    private MultipartFile picture;
    private MultipartFile signature;
//    private UserDTO userDTO;
}
