package com.webbanhangnongsan.vn.webbanhangnongsan.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(String toEmail, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);  // Enable HTML
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("chuhaiphong@gmail.com");

            mailSender.send(mimeMessage);
            System.out.println("Mail sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
