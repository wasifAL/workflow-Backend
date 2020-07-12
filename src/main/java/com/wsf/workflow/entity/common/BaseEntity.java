package com.wsf.workflow.entity.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}

