package com.wsf.workflow.service;

import com.wsf.workflow.repository.StagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StagesService {
    private final StagesRepository repository;
}
