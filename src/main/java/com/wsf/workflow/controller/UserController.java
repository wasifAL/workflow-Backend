package com.wsf.workflow.controller;


import com.wsf.workflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

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
