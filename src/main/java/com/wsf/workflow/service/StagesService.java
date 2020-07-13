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


    public Stages mapToEntity(StagesDTO stagesDTO) {
        return Stages.builder()
                .name(stagesDTO.getName())
                .description(stagesDTO.getDescription())
                .nextStage(null)
                .prevStage(null)
                .build();
    }

    public StagesDTO mapToDto(Stages stages) {
        return StagesDTO.builder()
                .name(stages.getName())
                .description(stages.getDescription())
                .build();
    }
}
