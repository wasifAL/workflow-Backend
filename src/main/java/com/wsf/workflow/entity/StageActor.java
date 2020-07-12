package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StageActor extends BaseEntity<Long> {
    @Nullable
    private String name;

    @Nullable
    private String description;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private Stages stage;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private EmployeeDetails employeeDetails; //employee
}
