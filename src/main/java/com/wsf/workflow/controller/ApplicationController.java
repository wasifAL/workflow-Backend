package com.wsf.workflow.controller;

import com.wsf.workflow.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application")
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
  /*  @PostMapping
    public void createApplication() {
    }

    @PostMapping
    public void applicationStageAction() {
    }

    @GetMapping
    public void getAll() {
    }

    @GetMapping
    public void getApplicationByID() {
    }*/
}
