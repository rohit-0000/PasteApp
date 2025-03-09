package com.pasteApp.paste.Controllers;

import com.pasteApp.paste.Entity.PasteEntry;
import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Services.AdminService;
import com.pasteApp.paste.Services.PasteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin")
public class AdminControllers {
    @Autowired
    AdminService adminService;
    @Autowired
    PasteServices pasteServices;

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody User user){
        adminService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("all-paste")
    public ResponseEntity<?> GetAllPaste(){
        List<PasteEntry> allPastes=adminService.getPastes();
        if(allPastes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(allPastes,HttpStatus.OK);
    }
    @GetMapping("all-user")
    public ResponseEntity<?> GetAllUSer(){
        List<User> allPastes=adminService.getUsers();
        if(allPastes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(allPastes,HttpStatus.OK);
    }

    @PostMapping("login")
    public void loginUser(@RequestBody User user){
        String userName=user.getUserName();
        String password=user.getPassword();

        
    }
}
