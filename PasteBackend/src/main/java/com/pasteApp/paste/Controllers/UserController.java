package com.pasteApp.paste.Controllers;

import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userInDb = userServices.findByuserName(userName);

            if(userInDb.getUserName()!=user.getUserName())
                userInDb.setUserName(user.getUserName());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(userInDb.getPassword()!=user.getPassword())
                userInDb.setPassword(user.getPassword());
            userServices.updateUser(userInDb);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new Exception("Exception :"+e);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByuserName(userName);
        userServices.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
