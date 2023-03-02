package com.wardasiu.project.wardasiu.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminPageController {
    @GetMapping
    public ModelAndView returnHomePage() {
        return new ModelAndView("admin");
    }

    @GetMapping("/current")
    public ModelAndView checkout(User user) {
        log.warn(user.getUsername());
        return new ModelAndView("admin");
    }

    @GetMapping("/current1")
    public ModelAndView checkout(Authentication authentication) {
        log.warn(authentication.getName());

        return new ModelAndView("admin");
    }
}