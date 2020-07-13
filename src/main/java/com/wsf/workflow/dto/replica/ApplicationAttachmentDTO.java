package com.wsf.workflow.dto.replica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationAttachmentDTO {
    private String documentName;
    private MultipartFile fileByte;
}
