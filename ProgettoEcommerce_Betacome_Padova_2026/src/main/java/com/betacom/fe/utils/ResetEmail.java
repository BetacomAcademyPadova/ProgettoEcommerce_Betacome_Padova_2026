package com.betacom.fe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ResetEmail {

    @Autowired
    private JavaMailSender sender;

    public void sendResetPasswordMail(String email, String nome, String token) {

        String link ="http://localhost:4200/dash/reset-password?token=" + token;

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject("Recupero password");

        msg.setText("""
                Ciao %s,
                Hai richiesto il recupero della password.
                Per impostarne una nuova clicca sul seguente link:
                %s
                
                Il link sarà valido per 15 minuti.
                Se non hai effettuato tu la richiesta puoi ignorare questa email.
                """.formatted(nome, link));

        sender.send(msg);
    }
}
