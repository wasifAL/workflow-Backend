package com.wsf.workflow.dto.replica;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StagesDTO {
    private Long ID;
    private String name;
    private String description;
    private Long nextStageID;
    private String nextStageName;
    private Long prevStageID;
    private String prevStageName;
}
