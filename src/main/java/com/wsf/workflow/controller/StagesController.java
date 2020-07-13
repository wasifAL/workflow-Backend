package com.wsf.workflow.controller;

import com.wsf.workflow.dto.StagesDTO;
import com.wsf.workflow.service.StagesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stage")
@AllArgsConstructor
@Slf4j
public class StagesController {

    private final StagesService stagesService;

    @PostMapping
    public ResponseEntity createStage(@RequestBody StagesDTO stagesDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stagesService.save(stagesDTO));
    }

    @GetMapping
    public ResponseEntity<List<StagesDTO>> getAllStages(){
        return ResponseEntity.status(HttpStatus.OK).body(stagesService.getAll());
    }

}
