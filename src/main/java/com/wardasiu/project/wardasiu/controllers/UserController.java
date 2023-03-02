package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ModelAndView registerUser(@RequestParam String usernameToRegister,
                                     @RequestParam String passwordToRegister,
                                     @RequestParam String emailToRegister) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (userService.isLoginAvailable(usernameToRegister)) {
            if (userService.isEmailAvailable(emailToRegister)) {
                userService.saveUser(new User(usernameToRegister, passwordToRegister, "NORMAL", emailToRegister));
            } else modelAndView.addObject("result", "Nazwa uzytkownika juz jest zajeta!");
        } else {
            modelAndView.addObject("result", "Email juz jest zajety!");
        }

        return modelAndView;
    }

}
