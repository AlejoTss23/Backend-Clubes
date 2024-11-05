package com.clubes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.service.imple.GmailService;

@RestController
@RequestMapping("/api/emails")
public class GmailController {

    @Autowired
    private GmailService gmailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody Map<String, Object> payload) {
        List<String> to = (List<String>) payload.get("to");  // Realizar un cast seguro a List<String>
        String subject = (String) payload.get("subject");
        String text = (String) payload.get("text");
        gmailService.sendSimpleEmail(to.toArray(new String[0]), subject, text);
        return ResponseEntity.ok().build();
    }
}



