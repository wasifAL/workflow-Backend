package com.wsf.workflow.service;

import com.wsf.workflow.repository.StageActionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StageActionService {
    private final StageActionRepository repository;
}
