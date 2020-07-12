package com.wsf.workflow.repository;

import com.wsf.workflow.entity.StageAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageActionRepository extends JpaRepository<StageAction, Long> {
}
