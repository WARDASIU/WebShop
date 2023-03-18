package com.wardasiu.project.wardasiu.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/personalizer")
public class PersonalizerController {
    @GetMapping
    public ModelAndView returnPersonalizerPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("personalizer");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        return modelAndView;
    }
}
