package io.meli.melimaps.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.meli.melimaps.interfaces.Observer;

@Service
public class EmailNotificationService implements Observer{

    private JavaMailSender mailSender;

    @Override
    public void update() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("");
        
    }
 


    
}
