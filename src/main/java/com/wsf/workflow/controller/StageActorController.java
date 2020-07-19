package com.wsf.workflow.controller;

import com.wsf.workflow.dto.replica.StageActorDTO;
import com.wsf.workflow.service.StageActorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stageActor")
@AllArgsConstructor
public class StageActorController {
    private final StageActorService stageActorService;

    @PostMapping
    public ResponseEntity<StageActorDTO> createStageActor(@RequestBody StageActorDTO stageActorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stageActorService.save(stageActorDTO));
    }

    @GetMapping
    public ResponseEntity<List<StageActorDTO>>  getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(stageActorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageActorDTO> getStageActorByID(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(stageActorService.getStageByID(id));
    }
}
