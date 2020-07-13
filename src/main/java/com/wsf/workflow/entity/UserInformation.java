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
@Builder
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
