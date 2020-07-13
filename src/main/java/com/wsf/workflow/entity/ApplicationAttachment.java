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
public class ApplicationAttachment extends BaseEntity<Long> {

    @Nullable
    private String documentName;

    @Nullable
    private String mimeType;

    @Nullable
    @Lob
    private byte[] fileByte;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;
}
