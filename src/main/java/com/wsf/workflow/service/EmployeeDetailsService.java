package com.wsf.workflow.service;

import com.wsf.workflow.repository.EmployeeDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeDetailsService {
    private final EmployeeDetailsRepository repository;

}
