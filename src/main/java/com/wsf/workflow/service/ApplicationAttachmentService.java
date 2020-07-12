package com.wsf.workflow.service;

import com.wsf.workflow.repository.ApplicationAttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationAttachmentService {
    private final ApplicationAttachmentRepository repository;
}
