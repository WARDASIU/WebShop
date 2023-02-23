package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }
}
