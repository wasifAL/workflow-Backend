package com.wsf.workflow.controller;


import com.wsf.workflow.dto.replica.UserDTO;
import com.wsf.workflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/emp")
    public ResponseEntity<List<UserDTO>> getAllEmp() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllemp());
    }

    //    get by user ID
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByID(id));
    }

}
