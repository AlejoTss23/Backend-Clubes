package com.clubes.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.clubes.model.EmailContact;
import com.clubes.repository.EmailContactRepository;

@Service
public class EmailContactService {

    @Autowired
    private EmailContactRepository emailContactRepository;

    @Autowired
    private JavaMailSender mailSender;

    public List<EmailContact> findAll() {
        return emailContactRepository.findAll();
    }

    public Optional<EmailContact> findById(Long id) {
        return emailContactRepository.findById(id);
    }

    public EmailContact save(EmailContact emailContact) {
        return emailContactRepository.save(emailContact);
    }

    public void deleteById(Long id) {
        emailContactRepository.deleteById(id);
    }

    public void sendEmail(String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}

