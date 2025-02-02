package com.example.service;

import java.awt.image.renderable.ContextualRenderedImageFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	//plain Test
	//html email -> design
	//from email,to email,subject,body
	
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	@Autowired
	public TemplateEngine templateEngine;
	
	
	public void sendPlainEmail(String fromEmail, String toString, String subject, String mailBody) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(fromEmail);
		message.setTo(toString);
		message.setSubject(subject);
		message.setText(mailBody);
		
		javaMailSender.send(message);
		
	}
	
	public void sendHtmlEmail(String fromEmail, String toString, String subject, String mailBody) throws Exception {
		
		MimeMessage message	= javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true ,"UTF-8");
		
		helper.setFrom(fromEmail);
		helper.setTo(toString);
		helper.setSubject(subject);
		helper.setText(mailBody);
		
		javaMailSender.send(message);
			
	}
	
	public void sendTemplateEmail(String fromEmail, String toString, String subject, String filename) throws Exception {
		
		Context context= new Context();
		context.setVariable("name", "pavan");
		
		String mailBody = templateEngine.process(filename, context);
		
		MimeMessage message	= javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true ,"UTF-8");
		
		helper.setFrom(fromEmail);
		helper.setTo(toString);
		helper.setSubject(subject);
		helper.setText(mailBody,true);
		
		javaMailSender.send(message);
			
	}
}












