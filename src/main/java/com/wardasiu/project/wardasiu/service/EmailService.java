package com.wardasiu.project.wardasiu.service;

import java.io.File;

public interface EmailService {
	void sendSimpleEmail(String receiver, String subject, String body);
	
	void sendSimpleEmailWithAttachments(String receiver, String subject, String body, File file, File... files);
	
	void sendHTMLEmail(String receiver, String subject, String htmlContent);

	void sendHTMLEmailWithAttachments(String receiver, String subject, String htmlContent, File file, File... files);
}