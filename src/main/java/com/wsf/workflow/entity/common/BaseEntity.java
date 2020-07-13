package com.wsf.workflow.entity.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity<ID> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private Long updatedBy;

    private Boolean isActive = true;

    private boolean isDeleted = false;

    public void setOnCreate(Long creatorId) {
        if (creatorId == null) creatorId = 1L;
        this.setCreatedAt(new Date());
        this.setCreatedBy(creatorId);
        this.setUpdatedAt(new Date());
        this.setUpdatedBy(creatorId);
        this.setIsActive(true);
        this.setDeleted(false);
    }

    public void setOnUpdate(Long updatorId) {
        if (updatorId == null) updatorId = 1L;
        this.setUpdatedAt(new Date());
        this.setUpdatedBy(updatorId);
    }
}

