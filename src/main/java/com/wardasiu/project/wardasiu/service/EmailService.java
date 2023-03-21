package com.wardasiu.project.wardasiu.service;

import java.io.File;
import java.util.List;

public interface EmailService {
	void sendNewsletter(List<String> receivers, String subject, String body);

	void sendSimpleEmail(String receiver, String subject, String body);
	
	void sendSimpleEmailWithAttachments(String receiver, String subject, String body, File file, File... files);
	
	void sendHTMLEmail(String receiver, String subject, String htmlContent);

	void sendHTMLEmailWithAttachments(String receiver, String subject, String htmlContent, File file, File... files);

    void sendHtmlNewsletter(List<String> receivers, String subject, String body);
}