package com.wardasiu.project.wardasiu.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/serviceOrder")
public class ServiceOrderController {
    @GetMapping
    public ModelAndView returnServicePage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("service");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        return modelAndView;
    }
}