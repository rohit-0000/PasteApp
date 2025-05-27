package com.pasteApp.paste.Controllers;

import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Services.UserDetailServiceImp;
import com.pasteApp.paste.Services.UserServices;
import com.pasteApp.paste.Utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/public")
@Slf4j
public class PublicControllers {
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User newUser){
        if(userServices.findByuserName(newUser.getUserName())!=null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            userServices.createNewUSer(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword())
            );
            UserDetails userDetails=userDetailServiceImp.loadUserByUsername(user.getUserName());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            System.out.println(jwt);
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Exception occured while createAuthenticationToken ",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User existingUser = userServices.findByEmail(email);
        if (existingUser != null) {
            existingUser.setPassword(passwordEncoder.encode(password));
            userServices.updateUser(existingUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/health-check")
    public String HealthCheck(){
        return "App is working fine";
    }
}
