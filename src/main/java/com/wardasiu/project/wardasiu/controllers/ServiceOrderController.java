package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.EmailService;
import com.wardasiu.project.wardasiu.service.InvoiceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/serviceOrder")
public class ServiceOrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @GetMapping
    public ModelAndView returnServicePage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("service");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<?> getChosenData(Authentication authentication,
                                           @RequestParam Map<String, String> values) {
        String receiver = values.get("email");
        if (authentication != null) {
            receiver = userService.findUserByUsername(authentication.getName()).getEmail();
        }
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h1>" + "Zamówienie usługi od Firmy EasyStep" + "</h1>");
        emailBody.append("<div style='margin: 2px 0; font-size: 1.3em; font-family: sans-serif;'");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.equals("") && !Objects.equals(key, "email")) {
                emailBody.append("<p>").append(key.replace("_", " ").toUpperCase()).append(": ").append("<i>").append(value.toUpperCase()).append("\n").append("</i>").append("</p>");
            }
        }
        emailBody.append("</div>");
        emailBody.append("<h5>W przeciągu dwóch dni roboczych nasi specjaliści skontaktują się z Państwem!</h5>");

        emailService.sendHTMLEmail(receiver, "Zamówienie usługi od Firmy EasyStep", String.valueOf(emailBody));

        return ResponseEntity.ok().build();
    }
}