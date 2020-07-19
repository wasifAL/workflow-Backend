package com.wsf.workflow.dto.replica;

//represents UserPayload

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;

    private Long roleId;
    private String fullName;
    private String address;
    private String mobile;

    private String designation;
    private MultipartFile picture;
    private MultipartFile signature;
}
