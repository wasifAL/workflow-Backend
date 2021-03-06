package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserInformation extends BaseEntity<Long> {

    @Nullable
    private String fullName;

    @Nullable
    private String address;

    @Nullable
    private String mobile;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
