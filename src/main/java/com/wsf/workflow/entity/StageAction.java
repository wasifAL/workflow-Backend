package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class StageAction extends BaseEntity<Long> {
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private StageActor currentStageActor;

    @ManyToOne(fetch = FetchType.LAZY)
    private StageActor nextStageActor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

}
