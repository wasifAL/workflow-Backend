package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Application extends BaseEntity<Long> {

    @Nullable
    private Double fund;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private StageActor stageActor;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserInformation applicant;

    @OrderBy("updatedAt DESC")
    @OneToMany(fetch = FetchType.LAZY)
    private List<StageAction> stageActionList;

    @OrderBy("updatedAt DESC")
    @OneToMany(fetch = FetchType.LAZY)
    private List<ApplicationAttachment> applicationAttachmentList;


}
