package com.wardasiu.project.wardasiu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailApi {
    private EmailService emailService;
    @Autowired
    public MailApi(EmailService emailService) {

        this.emailService = emailService;

    }

    @GetMapping("/sendMail")
    public String sendMail() throws MessagingException {
        emailService.sendEmail("pepe-pepe2044@wp.pl",
                "Wygrałeś",
                "<b>1000 000 zł</b><br>:P", true);
        return "wysłano";

    }
}

