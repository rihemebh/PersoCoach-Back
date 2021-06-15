package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.payload.request.AuthenticationRequest;
import com.website.persocoach.repositories.AdminRepository;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired private AdminRepository adminRepository;
    @Autowired private CoachRepository coachRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    @PostMapping("/coach/new")
    public ResponseEntity<?> newCoach(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();

        if(adminRepository.existsByUsername(authenticationRequest.getUsername())
                || clientRepository.existsByUsername(authenticationRequest.getUsername())
                || coachRepository.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminRepository.existsByEmail(authenticationRequest.getEmail())
                || clientRepository.existsByUsername(authenticationRequest.getEmail())
                || coachRepository.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }



        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_COACH)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Coach coach_user = new Coach(email,username,passwordEncoder.encode(password),roles);
        try{
            coachRepository.save(coach_user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("coach has been successfully signed up", HttpStatus.CREATED);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newAdmin(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();

        if(adminRepository.existsByUsername(authenticationRequest.getUsername())
                || clientRepository.existsByUsername(authenticationRequest.getUsername())
                || coachRepository.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminRepository.existsByEmail(authenticationRequest.getEmail())
                || clientRepository.existsByUsername(authenticationRequest.getEmail())
                || coachRepository.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }


        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Admin admin = new Admin(email,username,passwordEncoder.encode(password),roles);
        try{
            adminRepository.save(admin);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("admin has been successfully signed up", HttpStatus.CREATED);
    }
}



