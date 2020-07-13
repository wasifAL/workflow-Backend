package com.wsf.workflow.dto.replica;

import com.wsf.workflow.entity.Stages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageActorDTO {
    private String name;
    private String description;
    private Stages stage;
//    private EmployeeDetailsDTO employeeDetails;
}
