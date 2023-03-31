package com.wardasiu.project.wardasiu.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/verification")
    public ModelAndView getVerificationPage(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        boolean isLoggedIn = authentication != null;
        mav.addObject("isLoggedIn", isLoggedIn);

        mav.setViewName("verification");
        return mav;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
