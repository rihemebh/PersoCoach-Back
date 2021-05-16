package com.website.persocoach.controllers;


import com.website.persocoach.Models.User;
import com.website.persocoach.repositories.UserRepository;
import com.website.persocoach.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(users == null)
            return new ResponseEntity<String>("no users found",HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        User user;
        try{
         user = userRepository.findUserById(id);
        if(user == null){
            return new ResponseEntity<String>("User requested does not exist!", HttpStatus.BAD_REQUEST);
        }}catch(Exception e){
            return new ResponseEntity<String>("Unexpected Error id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        UserDetails user;
        try{
             user = userDetailsServiceImpl.loadUserByUsername(username);
        }catch (UsernameNotFoundException usernameNotFoundException){
            return new ResponseEntity<String>("User requested does not exist!", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>("Unexpected Error usr", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
    }
}
