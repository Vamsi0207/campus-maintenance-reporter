package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Models.User;
import com.example.Models.UserPrincipal;
import com.example.Repo.UserRepo;

@Service //Used for Security Configuration
public class MyUserDetailsService implements UserDetailsService {

     @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
      User user= userRepo.findByEmail(email);
        if(user==null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
       return new UserPrincipal(user);
    }
    

}
