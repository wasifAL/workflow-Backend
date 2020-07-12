package com.wsf.workflow.repository;

import com.wsf.workflow.entity.Stages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagesRepository extends JpaRepository<Stages, Long> {
}
