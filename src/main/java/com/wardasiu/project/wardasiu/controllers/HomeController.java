package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class HomeController {
    @GetMapping("/")
    public ModelAndView returnHomePage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("index");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        if (isLoggedIn) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
            modelAndView.addObject("isAdmin", isAdmin);
        }

        return modelAndView;
    }

    @GetMapping("/api/images/home/{filename}")
    @ResponseBody
    public byte[] getImage(@PathVariable String filename) throws IOException {
        String filePath = Paths.get("src/main/resources/img/home", filename).toString();
        return Files.readAllBytes(Paths.get(filePath));
    }
}