package com.pasteApp.paste.Services;

import com.pasteApp.paste.Entity.PasteEntry;
import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Repository.PasteRepo;
import com.pasteApp.paste.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  PasteRepo pasteRepo;

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepo.save(user);
    }

    public List<PasteEntry> getPastes(){
        return pasteRepo.findAll();
    }
    public  List<User> getUsers(){
        return userRepo.findAll();
    }
}
