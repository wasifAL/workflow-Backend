package com.wsf.workflow.service;

import com.wsf.workflow.dto.replica.StageActorDTO;
import com.wsf.workflow.dto.replica.StagesDTO;
import com.wsf.workflow.entity.StageActor;
import com.wsf.workflow.entity.Stages;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.StageActorRepository;
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

    public StageActor mapToEntity(StageActorDTO stageActorDTO) {
        StageActor stageActor = new StageActor();
        stageActor.setName(stageActorDTO.getName());

        stageActor.setOnCreate(1L);
        return stageActor;
    }

    public StageActorDTO mapToDto(StageActor stageActor) {
        StageActorDTO stageActorDTO = new StageActorDTO();
        stageActorDTO.setId(stageActor.getId());
        stageActorDTO.setName(stageActor.getName());
        return stageActorDTO;
    }
}
