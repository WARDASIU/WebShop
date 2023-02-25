package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostMapping
//    public ModelAndView login() {
//        ModelAndView modelAndView = new ModelAndView("index");
//        modelAndView.addObject("isLoggedIn", true);
//
//        return modelAndView;
//    }


    @GetMapping
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
