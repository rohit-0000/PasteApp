package com.pasteApp.paste.Controllers;

import com.pasteApp.paste.Entity.EmailRequest;
import com.pasteApp.paste.Services.EmailService;
import com.pasteApp.paste.Services.UserServices;
import com.pasteApp.paste.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailControllers {
    @Autowired
    EmailService emailService;
    @Autowired
    UserServices userServices;

    @PostMapping("/forgot-send")
    public ResponseEntity<?> sendForgotPassMail(@RequestBody EmailRequest emailRequest){
        try{
            User user = userServices.findByEmail(emailRequest.getTo());
            if(user != null && emailRequest.getUserName().equals(user.getUserName())) {
                emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailRequest emailRequest){
        try{
                emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
                return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}

