package com.zaparkuj.demo.services;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

public interface MailService {

    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException;
}
