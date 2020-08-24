package com.wsf.workflow.controller;

import com.wsf.workflow.dto.replica.ApplicationDTO;
import com.wsf.workflow.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    @PostMapping
    public ResponseEntity createApplication(@RequestBody ApplicationDTO applicationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.save(applicationDTO));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO>  getApplicationByID(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getApplicationByID(id));
    }
}
