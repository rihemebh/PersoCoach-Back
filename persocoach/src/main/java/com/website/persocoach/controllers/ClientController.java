package com.website.persocoach.controllers;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Role;
import com.website.persocoach.Models.RoleType;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/client")
public class ClientController {

    @Autowired private ClientRepository clientRepository;
    @Autowired private CoachRepository coachRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<?> addClient(@RequestBody Client client){

        if(clientRepository.existsByUsername(client.getUsername())
                || clientRepository.existsByUsername(client.getUsername())
                || coachRepository.existsByUsername(client.getUsername())
        ){
            return new ResponseEntity<String>("Username "+client.getUsername()+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminRepository.existsByEmail(client.getEmail())
                || clientRepository.existsByUsername(client.getEmail())
                || coachRepository.existsByUsername(client.getEmail())
        ){
            return new ResponseEntity<String>("Email "+client.getEmail()+" already exists", HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error : Role is not found."));
        roles.add(userRole);
        Client client_user = new Client(client.getEmail(),client.getUsername(),passwordEncoder.encode(client.getPassword()),roles);
        client_user.setAge(client.getAge());
        client_user.setDescription((client.getDescription()));
        client_user.setGender(client.getGender());
       // client_user.setHeight(client.getHeight());
       // client_user.setWeight(client.getWeight());
        client_user.setName(client.getName());
        client_user.setUrl(client.getUrl());

        try{
            clientRepository.save(client_user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("client has been successfully signed up", HttpStatus.CREATED);
    }

}

