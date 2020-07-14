package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StageAction extends BaseEntity<Long> {
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private StageActor currentStageActor;

    @ManyToOne(fetch = FetchType.LAZY)
    private StageActor nextStageActor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

}
