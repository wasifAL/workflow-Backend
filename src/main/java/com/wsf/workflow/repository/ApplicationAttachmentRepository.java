package com.wsf.workflow.repository;

import com.wsf.workflow.entity.ApplicationAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationAttachmentRepository extends JpaRepository<ApplicationAttachment, Long> {
}
