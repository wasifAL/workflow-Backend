package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeDetails extends BaseEntity<Long> {

    @Nullable
    private String fullName;

    @Nullable
    private String address;

    @Nullable
    private String designation;

    @Nullable
    private String mobile;

    @Nullable
    @Lob
    private byte[] picture;

    @Nullable
    private String ppMimeType;

    @Nullable
    @Lob
    private byte[] signature;

    @Nullable
    private String signatureMimeType;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
