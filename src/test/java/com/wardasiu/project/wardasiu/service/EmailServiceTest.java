package com.wardasiu.project.wardasiu.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class EmailServiceTest {
	@Autowired
	EmailService emailService;
	
	@Test
	void sendSimpleMail() {
		emailService.sendSimpleEmail("easystepapi@gmail.com", "Subject", "message");
	}
	
	@Test
	void sendMailWithAttachments() {
		File file = new File("src/main/resources/credentials.json");
		emailService.sendSimpleEmailWithAttachments("easystepapi@gmail.com", "Subject", "message", file);
	}
	
	@Test
	void sendMailWithHTMLContent() {
		String htmlContent = "<h1>Header</h1>" +
		                     "<table style=\"\n" +
		                     "    margin: 25px 0;\n" +
		                     "    font-size: 0.9em;\n" +
		                     "    font-family: sans-serif;\n" +
		                     "    color: rgb(20,150,20);\">\n" +
		                     "  <tr>\n" +
		                     "    <th>Company</th>\n" +
		                     "    <th>Contact</th>\n" +
		                     "    <th>Country</th>\n" +
		                     "  </tr>\n" +
		                     "  <tr>\n" +
		                     "    <td>Alfreds Futterkiste</td>\n" +
		                     "    <td>Maria Anders</td>\n" +
		                     "    <td>Germany</td>\n" +
		                     "  </tr>\n" +
		                     "  <tr>\n" +
		                     "    <td>Centro comercial Moctezuma</td>\n" +
		                     "    <td>Francisco Chang</td>\n" +
		                     "    <td>Mexico</td>\n" +
		                     "  </tr>\n" +
		                     "</table>";
		emailService.sendHTMLEmail("easystepapi@gmail.com", "Subject", htmlContent);
	}
	
	@Test
	void sendMailWithHTMLContentAndAttachments() {
		String htmlContent = "<h1>Header</h1>" +
		                     "<table style=\"\n" +
		                     "    margin: 25px 0;\n" +
		                     "    font-size: 0.9em;\n" +
		                     "    font-family: sans-serif;\n" +
		                     "    color: rgb(20,150,20);\">\n" +
		                     "  <tr>\n" +
		                     "    <th>Company</th>\n" +
		                     "    <th>Contact</th>\n" +
		                     "    <th>Country</th>\n" +
		                     "  </tr>\n" +
		                     "  <tr>\n" +
		                     "    <td>Alfreds Futterkiste</td>\n" +
		                     "    <td>Maria Anders</td>\n" +
		                     "    <td>Germany</td>\n" +
		                     "  </tr>\n" +
		                     "  <tr>\n" +
		                     "    <td>Centro comercial Moctezuma</td>\n" +
		                     "    <td>Francisco Chang</td>\n" +
		                     "    <td>Mexico</td>\n" +
		                     "  </tr>\n" +
		                     "</table>";
		File file = new File("invoice.pdf");
		emailService.sendHTMLEmailWithAttachments("easystepapi@gmail.com", "Subject", htmlContent, file);
	}
}