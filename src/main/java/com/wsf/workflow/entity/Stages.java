package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Stages extends BaseEntity<Long> {
    private String name;

    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private Stages nextStage;

    @OneToOne(fetch = FetchType.LAZY)
    private Stages prevStage;
}
