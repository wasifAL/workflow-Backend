package com.wsf.workflow.service;

import com.wsf.workflow.dto.StagesDTO;
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
    private final StagesRepository stagesRepository;

    @Transactional
    public StagesDTO save(StagesDTO stagesDTO) {
        Stages stages = mapToEntity(stagesDTO);
        stages.setOnCreate(null);
        try {
            stagesRepository.save(stages);
            log.info("Stage Created Successfully");
            stagesDTO.setID(stages.getId());
        } catch (Exception e) {
            throw new CustomException("Couldn't Create Stage");
        }
        return stagesDTO;
    }

    @Transactional(readOnly = true)
    public List<StagesDTO> getAll() {
        return stagesRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public StagesDTO getStageByID(Long id) {
        return mapToDto(stagesRepository.getOne(id));
    }

    public Stages mapToEntity(StagesDTO stagesDTO) {
        Stages stage = new Stages();
        stage.setName(stagesDTO.getName());
        stage.setDescription(stagesDTO.getDescription());
        stage.setNextStage(null);
        stage.setPrevStage(null);
        return stage;
    }

    public StagesDTO mapToDto(Stages stages) {
        return StagesDTO.builder()
                .name(stages.getName())
                .description(stages.getDescription())
                .build();
    }
}
