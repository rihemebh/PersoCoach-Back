package com.website.persocoach.controllers;


import com.website.persocoach.Models.User;
import com.website.persocoach.repositories.UserRepository;
import com.website.persocoach.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired UserRepository userRepository;

    @GetMapping("/api/user/all")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(users == null)
            return new ResponseEntity<String>("no users found",HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
