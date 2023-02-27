package com.wardasiu.project.wardasiu.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping
@Slf4j
public class LoginController {
    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
