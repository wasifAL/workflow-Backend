package com.wsf.workflow.dto.replica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDTO {
    private String fullName;
    private String address;
    private String mobile;
//    private UserDTO user;
}
