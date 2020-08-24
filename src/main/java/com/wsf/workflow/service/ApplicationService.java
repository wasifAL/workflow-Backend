package com.wsf.workflow.service;

import com.wsf.workflow.dto.replica.ApplicationDTO;
import com.wsf.workflow.entity.Application;
import com.wsf.workflow.entity.StageActor;
import com.wsf.workflow.repository.*;
import org.springframework.security.core.userdetails.User;
import com.wsf.workflow.entity.UserInformation;
import com.wsf.workflow.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class ApplicationService {
    private final ApplicationRepository repository;
    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;
    private final StageActorRepository stageActorRepository;

    @Transactional
    public ApplicationDTO save(ApplicationDTO applicationDTO) {
        Application application = mapToEntity(applicationDTO);
        application.setOnCreate(null);
        try {
            repository.save(application);
            log.info("Application Created Successfully");
            applicationDTO.setId(application.getId());
        } catch (Exception e) {
            throw new CustomException("Couldn't Create Application");
        }
        return applicationDTO;
    }

    @Transactional(readOnly = true)
    public List<ApplicationDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public ApplicationDTO getApplicationByID(Long id) {
        return mapToDto(repository.getOne(id));
    }

    public Application mapToEntity(ApplicationDTO applicationDTO) {
        Application application = new Application();
        if (applicationDTO.getId() != null && applicationDTO.getId() > 0) {
            application = repository.getOne(applicationDTO.getId());
            application.setOnUpdate(1L);
        } else {
            application.setOnCreate(1L);
        }
        application.setOfficeName(applicationDTO.getOfficeName());
        application.setOfficeAddress(applicationDTO.getOfficeAddress());
        application.setDesignation(applicationDTO.getDesignation());
        application.setReqfund(applicationDTO.getReqfund());
        application.setYearlyIncome(applicationDTO.getYearlyIncome());
        application.setAppfund(applicationDTO.getAppfund());
        application.setApplicant(getApplicant());
        application.setStageActor(getStageActor());
        return application;
    }

    public ApplicationDTO mapToDto(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setOfficeName(application.getOfficeName());
        applicationDTO.setOfficeAddress(application.getOfficeAddress());
        applicationDTO.setDesignation(application.getDesignation());
        applicationDTO.setReqfund(application.getReqfund());
        applicationDTO.setYearlyIncome(application.getYearlyIncome());
        applicationDTO.setAppfund(application.getAppfund());
        applicationDTO.setApplicantId(application.getApplicant().getId());
        applicationDTO.setApplicantDetails(application.getApplicant().getFullName());
        if (application.getStageActionList() != null) {
            applicationDTO.setActionCount(application.getStageActionList().size());
        }
        applicationDTO.setStageActorId(application.getStageActor().getId());
        applicationDTO.setStageActorDetails(application.getStageActor().getName());
        return applicationDTO;
    }

    @Transactional(readOnly = true)
    public UserInformation getApplicant() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInformationRepository.getUserInformationByUser(userRepository.findByUsername(principal.getUsername()));
    }

    @Transactional(readOnly = true)
    public StageActor getStageActor() {
        return stageActorRepository.getStageActor();
    }
}
