package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.payload.request.AuthenticationRequest;
import com.website.persocoach.services.AdminService;
import com.website.persocoach.services.ClientService;
import com.website.persocoach.services.CoachesService;
import com.website.persocoach.services.RoleService;
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

    @Autowired private AdminService adminService;
    @Autowired private CoachesService coachesService;
    @Autowired private ClientService clientService;
    @Autowired private RoleService roleService;
    @Autowired private PasswordEncoder passwordEncoder;


    @PostMapping("/coach/new")
    public ResponseEntity<?> newCoach(@RequestBody AuthenticationRequest authenticationRequest){
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String email = authenticationRequest.getEmail();

        if(adminService.existsByUsername(authenticationRequest.getUsername())
                || clientService.existsByUsername(authenticationRequest.getUsername())
                || coachesService.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminService.existsByEmail(authenticationRequest.getEmail())
                || clientService.existsByUsername(authenticationRequest.getEmail())
                || coachesService.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }



        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleType.ROLE_COACH)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Coach coach_user = new Coach(email,username,passwordEncoder.encode(password),roles);
        try{
            coachesService.save(coach_user);
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

        if(adminService.existsByUsername(authenticationRequest.getUsername())
                || clientService.existsByUsername(authenticationRequest.getUsername())
                || coachesService.existsByUsername(authenticationRequest.getUsername())
        ){
            return new ResponseEntity<String>("Username "+username+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminService.existsByEmail(authenticationRequest.getEmail())
                || clientService.existsByUsername(authenticationRequest.getEmail())
                || coachesService.existsByUsername(authenticationRequest.getEmail())
        ){
            return new ResponseEntity<String>("Email "+email+" already exists", HttpStatus.BAD_REQUEST);
        }


        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Admin admin = new Admin(email,username,passwordEncoder.encode(password),roles);
        try{
            adminService.save(admin);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("admin has been successfully signed up", HttpStatus.CREATED);
    }
}



