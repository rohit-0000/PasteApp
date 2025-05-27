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

            if(user.getUserName()!=null && userInDb.getUserName()!=user.getUserName())
                userInDb.setUserName(user.getUserName());

            if(user.getPassword()!=null &&!passwordEncoder.matches(user.getPassword(),userInDb.getPassword()))
                userInDb.setPassword(passwordEncoder.encode(user.getPassword()));

            if(user.getName()!=null && userInDb.getName()!=user.getName())
                userInDb.setName(user.getName());

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
    @GetMapping()
    public ResponseEntity<String> getName(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();

        User user=userServices.findByuserName(userName);
        return new ResponseEntity<>(user.getName(),HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetail(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();

        User user=userServices.findByuserName(userName);
        if (user!=null)
            return new ResponseEntity<>(user,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
