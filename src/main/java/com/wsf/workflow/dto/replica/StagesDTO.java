package com.wsf.workflow.dto.replica;

//Represents StagePayload in front end

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StagesDTO {
    private Long id;
    private String name;
    private String description;
    private Integer seq;
    private Long nextStageId;
    private String nextStageName;
    private Long prevStageId;
    private String prevStageName;
}
