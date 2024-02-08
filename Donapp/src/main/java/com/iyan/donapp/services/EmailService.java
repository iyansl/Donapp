package com.iyan.donapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import jakarta.mail.internet.MimeMessage;

@Configuration
public class EmailService
{
	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String to, String subject, String text) {  

		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {  
			public void prepare(MimeMessage mimeMessage) throws Exception {  
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("donapp-00@outlook.com");
				message.setTo(to);
				message.setSubject(subject);
				message.setText(text, true);
			}  
		};  

		mailSender.send(messagePreparator);  
	}
}