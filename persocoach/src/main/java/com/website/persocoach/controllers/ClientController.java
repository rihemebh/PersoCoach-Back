package com.website.persocoach.controllers;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.Models.Role;
import com.website.persocoach.Models.RoleType;
import com.website.persocoach.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
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
    @Autowired private RequestRepository requestRepository;

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
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
        client_user.setHeight(client.getHeight());
        client_user.setWeight(client.getWeight());
        client_user.setName(client.getName());
        client_user.setUrl(client.getUrl());

        try{
            clientRepository.save(client_user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("client has been successfully signed up", HttpStatus.CREATED);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/programs/client_id/{id}")
    public ResponseEntity<?> getProgramsByClientId(@PathVariable String id){
        Client client = clientRepository.getClientById(id);
        if(client==null){
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        try {
            List<ProgramRequest> list = requestRepository.getProgramRequestsByClient_Id(id);
            return new ResponseEntity<List<ProgramRequest>>(list, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>("error getting client requests", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public ResponseEntity<?> updateClientInfo(@RequestBody Client client
                                              ){
        try {


            clientRepository.save(client);

            if (client== null){
                return new ResponseEntity<String>("error updating", HttpStatus.BAD_REQUEST);
            }
            //Client updatedClient = clientRepository.save(existingClient);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>("err", HttpStatus.BAD_REQUEST);
        }
    }

}

