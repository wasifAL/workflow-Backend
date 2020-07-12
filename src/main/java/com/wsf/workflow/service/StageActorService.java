package com.wsf.workflow.service;

import com.wsf.workflow.repository.StageActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StageActorService {
    private final StageActorRepository repository;
}
