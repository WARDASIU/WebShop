package com.wardasiu.project.wardasiu.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
public class AdminPageController {
    @GetMapping
    public ModelAndView getAdminPage(){
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("user");

        return modelAndView;
    }
}
