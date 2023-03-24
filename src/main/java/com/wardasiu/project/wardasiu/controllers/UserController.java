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

import java.util.*;
import java.util.stream.Collectors;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestParam String usernameToRegister,
                                            @RequestParam String passwordToRegister,
                                            @RequestParam String emailToRegister) {
        Map<String, Object> response = new HashMap<>();
        if (userService.isLoginAvailable(usernameToRegister)) {
            if (userService.isEmailAvailable(emailToRegister)) {
                userService.saveUser(new User(usernameToRegister, passwordToRegister, "NORMAL", emailToRegister));
            } else {
                response.put("errors", Collections.singletonMap("emailTaken", true));
            }
        } else {
            response.put("errors", Collections.singletonMap("loginTaken", true));
        }
        return response;
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

    @PostMapping("/admin/sendMail")
    public ResponseEntity<?> sendNewsletter(@RequestParam String title,
                                            @RequestParam String subject,
                                            @RequestParam(defaultValue = "false") String inHtml) {
        List<String> userEmails = userService.getUsersWithNewsletter()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        if (Objects.equals(inHtml, "false")) {
            emailService.sendNewsletter(userEmails, title, subject);
        } else {
            emailService.sendHtmlNewsletter(userEmails, title, subject);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("message", "Newsletter został wysłany!");

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/checkLogin")
    public boolean isLoggedIn(Authentication authentication){
        return authentication != null;
    }
}