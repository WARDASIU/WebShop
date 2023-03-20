package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

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

    @GetMapping("/profile")
    public ModelAndView getProfilePage(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        User user = userService.findUserByUsername(authentication.getName());

        mav.addObject("user", user);

        return mav;
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, Object> updateFields, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.updateUser(user, updateFields);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/sendMail")
    public ResponseEntity<?> sendNewsletter() {
        emailService.sendSimpleEmail("pepe-pepe2044@wp.pl","ABC","ABC");

        return ResponseEntity.ok().build();
    }
}