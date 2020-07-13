package com.wsf.workflow.dto.replica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonDTO {
    private Long id;
    private Date createdAt;
    private Long createdBy;
    private Date updatedAt;
    private Long updatedBy;
    private Boolean isActive;
    private boolean isDeleted;
}
