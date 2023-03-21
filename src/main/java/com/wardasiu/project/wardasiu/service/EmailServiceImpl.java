package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.service.gmail.GMailIntegration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    private GMailIntegration gMailIntegration;

    public EmailServiceImpl() {
        try {
            this.gMailIntegration = new GMailIntegration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendNewsletter(final List<String> receivers, final String subject, final String body) {
        for (String receiver : receivers) {
            sendSimpleEmail(receiver, subject, body);
        }
    }

    @Override
    public void sendHtmlNewsletter(final List<String> receivers, final String subject, final String body) {
        for (String receiver : receivers) {
            sendHTMLEmail(receiver, subject, body);
        }
    }

    @Override
    public void sendSimpleEmail(String recipient, String subject, String message) {
        try {
            gMailIntegration.sendMail(recipient, subject, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendSimpleEmailWithAttachments(String recipient, String subject, String message, File file,
                                               File... files) {
        try {
            gMailIntegration.sendMailWithAttachments(recipient, subject, message, file, files);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendHTMLEmail(String recipient, String subject, String htmlContent) {
        try {
            gMailIntegration.sendMailWithHTMLContent(recipient, subject, htmlContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendHTMLEmailWithAttachments(String recipient, String subject, String htmlContent, File file,
                                             File... files) {
        try {
            gMailIntegration.sendMailWithHTMLContentAndAttachments(recipient, subject, htmlContent, file, files);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}