package com.Bookhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendReviewNotification(String toEmail, String bookTitle, String comment) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Nueva reseña creada en BookHub");
        message.setText(
                "Has creado una reseña para el libro: " + bookTitle +
                "\n\nComentario:\n" + comment +
                "\n\nGracias por usar BookHub!"
        );

        mailSender.send(message);
    }
}