package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Models.User;
import com.example.Repo.UserRepo;

@Service
public class Changepassword {

    @Autowired
    private UserRepo userrepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        User user = userrepo.findByEmail(email);
        if (user == null) {
            return false; // User not found
        }

        // Verify old password
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return false; // Old password incorrect
        }

        // Encode and set new password
        user.setPassword(encoder.encode(newPassword));
        userrepo.save(user);
        return true; // Password change successful
    }
}
