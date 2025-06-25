package com.example.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Models.User;

public interface UserRepo extends JpaRepository<User,String> {
    User findByEmail(String email);
}
