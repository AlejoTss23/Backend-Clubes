package com.clubes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.model.EmailContact;
import com.clubes.service.imple.EmailContactService;

@RestController
@RequestMapping("/api/email-contacts")
public class EmailContactController {

    @Autowired
    private EmailContactService emailContactService;

    @GetMapping
    public List<EmailContact> getAllEmailContacts() {
        return emailContactService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailContact> getEmailContactById(@PathVariable Long id) {
        return emailContactService.findById(id)
                .map(emailContact -> ResponseEntity.ok().body(emailContact))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailContact createEmailContact(@RequestBody EmailContact emailContact) {
        return emailContactService.save(emailContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailContact> updateEmailContact(@PathVariable Long id, @RequestBody EmailContact emailContactDetails) {
        return emailContactService.findById(id)
                .map(emailContact -> {
                    emailContact.setNombre(emailContactDetails.getNombre());
                    emailContact.setEmail(emailContactDetails.getEmail());
                    EmailContact updatedEmailContact = emailContactService.save(emailContact);
                    return ResponseEntity.ok().body(updatedEmailContact);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailContact(@PathVariable Long id) {
        return emailContactService.findById(id)
                .map(emailContact -> {
                    emailContactService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody Map<String, Object> request) {
        List<String> to = (List<String>) request.get("to");
        String subject = (String) request.get("subject");
        String text = (String) request.get("text");

        emailContactService.sendEmail(to.toArray(new String[0]), subject, text);

        return ResponseEntity.ok().build();
    }
}
