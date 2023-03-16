package com.wardasiu.project.wardasiu.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/serviceOrder")
public class ServiceOrderController {
    @GetMapping
    public ModelAndView returnHomePage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("service");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        return modelAndView;
    }
}