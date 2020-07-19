package com.wsf.workflow.service;

import com.wsf.workflow.dto.replica.StageActorDTO;
import com.wsf.workflow.dto.replica.StagesDTO;
import com.wsf.workflow.entity.StageActor;
import com.wsf.workflow.entity.Stages;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.EmployeeDetailsRepository;
import com.wsf.workflow.repository.StageActorRepository;
import com.wsf.workflow.repository.StagesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class StageActorService {
    private final StageActorRepository repository;
    private final EmployeeDetailsRepository employeeDetailsRepository;
    private final StagesRepository stagesRepository;


    @Transactional
    public StageActorDTO save(StageActorDTO stageActorDTO) {
        StageActor stageActor = mapToEntity(stageActorDTO);
        stageActor.setOnCreate(null);
        try {
            repository.save(stageActor);
            log.info("Stage Created Successfully");
            stageActorDTO.setId(stageActor.getId());
        } catch (Exception e) {
            throw new CustomException("Couldn't Create Stage");
        }
        return stageActorDTO;
    }

    @Transactional(readOnly = true)
    public List<StageActorDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public StageActorDTO getStageByID(Long id) {
        return mapToDto(repository.getOne(id));
    }

    @Transactional(readOnly = true)
    public StageActor mapToEntity(StageActorDTO stageActorDTO) {
        StageActor stageActor = new StageActor();
        stageActor.setName(stageActorDTO.getName());
        stageActor.setDescription(stageActorDTO.getDescription());
        if (stageActorDTO.getEmployeeId() != null) {
            stageActor.setEmployeeDetails(employeeDetailsRepository.getOne(stageActorDTO.getEmployeeId()));
        }
        if (stageActorDTO.getStageId() != null) {
            stageActor.setStage(stagesRepository.getOne(stageActorDTO.getStageId()));
        }

        stageActor.setOnCreate(1L);
        return stageActor;
    }

    public StageActorDTO mapToDto(StageActor stageActor) {
        StageActorDTO stageActorDTO = new StageActorDTO();
        stageActorDTO.setId(stageActor.getId());
        stageActorDTO.setName(stageActor.getName());
        stageActorDTO.setDescription(stageActor.getDescription());
        if (stageActor.getEmployeeDetails() != null) {
            stageActorDTO.setEmployeeId(stageActor.getEmployeeDetails().getId());
            stageActorDTO.setEmployeeDetails(stageActor.getEmployeeDetails().getFullName() + " (" + stageActor.getEmployeeDetails().getDesignation() + ")");
        }
        if (stageActor.getStage() != null) {
            stageActorDTO.setStageId(stageActor.getStage().getId());
            stageActorDTO.setStageName(stageActor.getStage().getName());
        }
        return stageActorDTO;
    }
}
