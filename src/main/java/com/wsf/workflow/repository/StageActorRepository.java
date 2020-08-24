package com.wsf.workflow.repository;

import com.wsf.workflow.entity.StageActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StageActorRepository extends JpaRepository<StageActor, Long> {
    @Query("select sa from StageActor sa order by sa.stage.seq desc")
    StageActor getStageActor();
}
