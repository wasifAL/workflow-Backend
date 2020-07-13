package com.wsf.workflow.controller;

import com.wsf.workflow.entity.StageActor;
import com.wsf.workflow.service.StageActorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stageActor")
@AllArgsConstructor
public class StageActorController {
    private final StageActorService stageActorService;

  /*  @PostMapping
    public void createStageActor() {
    }

    @GetMapping
    public void getAll() {
    }

    @GetMapping
    public void getStageActorByID() {
    }*/
}
