package com.wardasiu.project.wardasiu.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StaticPagesController {
    @GetMapping("/knowledgeBase")
    public ModelAndView returnKnowledgeBase(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("knowledgeBase");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        return modelAndView;
    }
}