package com.pasteApp.paste.Services;

import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Repository.PasteRepo;
import com.pasteApp.paste.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasteRepo pasteRepo;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public User findByuserName(String userName){
        return userRepo.findByUserName(userName);
    }
    public void createNewUSer(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        user.setPasteEntries(new ArrayList<>());
        userRepo.save(user);
    }

    public void updateUser(User user){
        userRepo.save(user);
    }
    public void removePasteRef(User user){
        userRepo.save(user);
    }
    public void deleteUser(User user) {
        user.getPasteEntries().forEach(Entry -> pasteRepo.deleteById(Entry.get_id()));
        userRepo.deleteByUserName(user.getUserName());
    }
    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
