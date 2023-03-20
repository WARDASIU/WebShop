package com.wardasiu.project.wardasiu.service.gmail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class GMailIntegration {
	private static final String SENDER_EMAIL = "easystepapi@gmail.com";
	private final Gmail service;
	
	public GMailIntegration() throws Exception {
		NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
		service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
				.setApplicationName("Test Mailer")
				.build();
	}

	public void sendMail(String receiver, String subject, String message) throws Exception {
		MimeMessage email = createEmail(receiver, subject, message);
		sendMessage(email);
	}
	
	public void sendMailWithAttachments(String receiver, String subject, String message, File file, File... files)
			throws Exception {
		MimeMessage email = createEmail(receiver, subject, "");
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(message);
		
		MimeBodyPart attachmentPart = new MimeBodyPart();
		attachmentPart.attachFile(file);
		for (File singleFile : files) {
			attachmentPart.attachFile(singleFile);
		}
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		multipart.addBodyPart(attachmentPart);
		
		email.setContent(multipart);
		sendMessage(email);
	}
	
	public void sendMailWithHTMLContent(String receiver, String subject, String htmlContent) throws Exception {
		MimeMessage email = createEmail(receiver, subject, "");
		email.setContent(htmlContent, "text/html; charset=utf-8");
		sendMessage(email);
	}
	
	public void sendMailWithHTMLContentAndAttachments(String receiver, String subject, String htmlContent, File file,
	                                                  File... files) throws Exception {
		MimeMessage email = createEmail(receiver, subject, "");
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
		
		MimeBodyPart attachmentPart = new MimeBodyPart();
		attachmentPart.attachFile(file);
		for (File singleFile : files) {
			attachmentPart.attachFile(singleFile);
		}
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		multipart.addBodyPart(attachmentPart);
		
		email.setContent(multipart);
		sendMessage(email);
	}
	
	public static MimeMessage createEmail(String receiver, String subject, String message) throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		MimeMessage email = new MimeMessage(session);
		
		email.setFrom(new InternetAddress(SENDER_EMAIL));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(receiver));
		email.setSubject(subject);
		email.setText(message);
		return email;
	}
	
	public static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}
	
	private void sendMessage(MimeMessage email) throws MessagingException, IOException {
		Message messageWithEmail = createMessageWithEmail(email);
		
		try {
			messageWithEmail = service.users().messages().send("me", messageWithEmail).execute();
			System.out.println("Message id: " + messageWithEmail.getId());
			System.out.println(messageWithEmail.toPrettyString());
		} catch (GoogleJsonResponseException e) {
			GoogleJsonError error = e.getDetails();
			if (error.getCode() == 403) {
				System.err.println("Unable to send message: " + e.getDetails());
			} else {
				throw e;
			}
		}
	}
	
	private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
			throws IOException {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(
				Objects.requireNonNull(GMailIntegration.class.getResourceAsStream("/credentials.json"))));
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
				.setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
				.setAccessType("offline")
				.build();
		
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
	
}