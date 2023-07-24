package com.phone.controller;

import com.phone.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/email")
public class MailSendingController {

    @Autowired
    public JavaMailSender javaMailSender;


    @PostMapping("/contact")
    public ResponseEntity<String> sendPhoneEntry(@RequestBody Contact contact) {
        try {
            // Compose the email message
            StringBuilder messageBody = new StringBuilder();
            messageBody.append("Email:").append(contact.getEmail()).append("\n");
            messageBody.append("Name: ").append(contact.getName()).append("\n");
            messageBody.append("Phone Number: ").append(contact.getPhoneNumber()).append("\n");
            // Add other fields as needed

            // Create the email message using Spring's MimeMessageHelper
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo("nareshkumarkakollu3456@gmail.com"); // Replace with the recipient's email address
            helper.setSubject("Contact Information");
            helper.setText(messageBody.toString());

            // Send the email
            javaMailSender.send(mimeMessage);

            return ResponseEntity.ok("Phone entry sent successfully!");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send the phone entry: " + e.getMessage());
        }
    }
}
