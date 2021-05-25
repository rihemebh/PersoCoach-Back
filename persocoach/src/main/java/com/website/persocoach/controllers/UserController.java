package com.website.persocoach.controllers;


import com.website.persocoach.Models.Admin;
import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.User;
import com.website.persocoach.repositories.AdminRepository;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.UserRepository;
import com.website.persocoach.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired UserRepository userRepository;
    @Autowired CoachRepository coachRepository;
    @Autowired ClientRepository clientRepository;
    @Autowired AdminRepository adminRepository;



    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        try{
         Optional<Coach> coach_user = coachRepository.findById(id);
         if(coach_user.isEmpty()){
            Optional<Client> client_user = clientRepository.findById(id);
            if(client_user.isEmpty()){
                Optional<Admin> admin_user = adminRepository.findById(id);
                if(admin_user.isEmpty()){
                    return new ResponseEntity<String>("User requested does not exist!", HttpStatus.BAD_REQUEST);
                }else{
                    return new ResponseEntity<Optional<Admin>>(admin_user, HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<Optional<Client>>(client_user, HttpStatus.OK);
            }
        }else{
             return new ResponseEntity<Optional<Coach>>(coach_user, HttpStatus.OK);
         }
        }catch(Exception e){
            return new ResponseEntity<String>("Unexpected Error id", HttpStatus.BAD_REQUEST);
        }
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
