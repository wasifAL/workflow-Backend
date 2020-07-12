package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
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
public class Stages extends BaseEntity<Long> {
    private String name;

    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Stages nextStage;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Stages prevStage;
}
