package com.pasteApp.paste.Services;

import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImp implements UserDetailsService {//load its function

    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUserName(username);
        if(user!=null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder() //just enter User and select User by spring-security
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        else throw new UsernameNotFoundException("User not found with username :"+username);
    }
}
