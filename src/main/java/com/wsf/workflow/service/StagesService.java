package com.wsf.workflow.service;

import com.wsf.workflow.dto.replica.StagesDTO;
import com.wsf.workflow.entity.Stages;
import com.wsf.workflow.exception.CustomException;
import com.wsf.workflow.repository.StagesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class StagesService {
    private final StagesRepository repository;

    @Transactional
    public StagesDTO save(StagesDTO stagesDTO) {
        Stages stages = mapToEntity(stagesDTO);
        stages.setOnCreate(null);
        try {
            repository.save(stages);
            log.info("Stage Created Successfully");
            stagesDTO.setID(stages.getId());
        } catch (Exception e) {
            throw new CustomException("Couldn't Create Stage");
        }
        return stagesDTO;
    }

    @Transactional(readOnly = true)
    public List<StagesDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public StagesDTO getStageByID(Long id) {
        return mapToDto(repository.getOne(id));
    }

    public Stages mapToEntity(StagesDTO stagesDTO) {
        Stages stage = new Stages();
        stage.setName(stagesDTO.getName());
        stage.setDescription(stagesDTO.getDescription());
        stage.setSeq(stagesDTO.getSeq());
        if(stagesDTO.getNextStageID()!=null && stagesDTO.getNextStageID()>0){
            stage.setNextStage(repository.getOne(stagesDTO.getNextStageID()));
        }
        if(stagesDTO.getPrevStageID()!=null && stagesDTO.getPrevStageID()>0){
            stage.setPrevStage(repository.getOne(stagesDTO.getPrevStageID()));
        }
        stage.setOnCreate(1L);
        return stage;
    }

    public StagesDTO mapToDto(Stages stages) {
        StagesDTO stagesDTO = new StagesDTO();
        stagesDTO.setID(stages.getId());
        stagesDTO.setName(stages.getName());
        stagesDTO.setDescription(stages.getDescription());
        stagesDTO.setSeq(stages.getSeq());
        if(stages.getNextStage() != null){
            stagesDTO.setNextStageID(stages.getNextStage().getId());
            stagesDTO.setNextStageName(stages.getNextStage().getName());
        }
        if(stages.getPrevStage() != null){
            stagesDTO.setPrevStageID(stages.getPrevStage().getId());
            stagesDTO.setPrevStageName(stages.getPrevStage().getName());
        }

        return stagesDTO;
    }
}
