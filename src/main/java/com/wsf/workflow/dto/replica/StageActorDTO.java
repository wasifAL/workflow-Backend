package com.wsf.workflow.dto.replica;

//represents StageActorPayload

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageActorDTO {
    private Long id;
    private String name;
    private String description;
    private Long stageId;
    private String stageName;
    private Long employeeId;
    private String employeeDetails;
}
