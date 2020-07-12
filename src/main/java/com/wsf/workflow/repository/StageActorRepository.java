package com.wsf.workflow.repository;

import com.wsf.workflow.entity.StageActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageActorRepository extends JpaRepository<StageActor, Long> {
}
