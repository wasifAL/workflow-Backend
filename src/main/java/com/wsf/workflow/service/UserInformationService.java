package com.wsf.workflow.service;

import com.wsf.workflow.repository.UserInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInformationService {
    private final UserInformationRepository repository;
}
