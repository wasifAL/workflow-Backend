package com.wsf.workflow.service;

import com.wsf.workflow.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final ApplicationRepository repository;
}
