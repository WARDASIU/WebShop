package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final UserService userService;

    public LoginController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ModelAndView login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView modelAndView = new ModelAndView();

        // Check if user exists in the database and password is correct
        if (userService.authenticateUser(username, password) != null) {
            UserDetails user = userService.loadUserByUsername(username);
            modelAndView.setViewName("redirect:/");
            modelAndView.addObject("user", user);
            modelAndView.addObject("authentication", true);
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("loginError", true);
        }

        return modelAndView;
    }


    @GetMapping
    public ModelAndView getLoginPage(boolean loginError) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("loginError", loginError);
        return mav;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
