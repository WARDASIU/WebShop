package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.service.gmail.GMailIntegration;
import org.springframework.stereotype.Service;

import java.io.File;

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
