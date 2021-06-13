package com.website.persocoach.controllers;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.Models.Role;
import com.website.persocoach.Models.RoleType;
import com.website.persocoach.services.*;
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

    @Autowired private AdminService adminService;
    @Autowired private CoachesService coachesService;
    @Autowired private ClientService clientService;
    @Autowired private RoleService roleService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RequestService requestService;

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addClient(@RequestBody Client client){

        if(adminService.existsByUsername(client.getUsername())
                || clientService.existsByUsername(client.getUsername())
                || coachesService.existsByUsername(client.getUsername())
        ){
            return new ResponseEntity<String>("Username "+client.getUsername()+" already exists", HttpStatus.BAD_REQUEST);
        }

        if(adminService.existsByEmail(client.getEmail())
                || clientService.existsByUsername(client.getEmail())
                || coachesService.existsByUsername(client.getEmail())
        ){
            return new ResponseEntity<String>("Email "+client.getEmail()+" already exists", HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleType.ROLE_CLIENT)
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
            clientService.save(client_user);
        }catch(Exception e){
            return new ResponseEntity<String>("error while creating coach", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<String>("client has been successfully signed up", HttpStatus.CREATED);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/programs/client_id/{id}")
    public ResponseEntity<?> getProgramsByClientId(@PathVariable String id){
        Client client = clientService.getClientById(id);
        if(client==null){
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        try {
            List<ProgramRequest> list = requestService.getProgramRequestsByClient_Id(id);
            return new ResponseEntity<List<ProgramRequest>>(list, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>("error getting client requests", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public ResponseEntity<?> updateClientInfo(@RequestParam String id,
                                              @RequestParam String name,
                                              @RequestParam String description,
                                              @RequestParam String pwd){
        try {
            Client client1 = clientService.getClientById(id);

            if (client1== null){
                return new ResponseEntity<String>("error updating", HttpStatus.BAD_REQUEST);
            }
            if(name != null){
                client1.setName(name);
            }
            if(description != null){
                client1.setDescription(description);
            }
            if(pwd != null){
                client1.setPassword(passwordEncoder.encode(pwd));
            }

            Client updatedClient = clientService.save(client1);
            return new ResponseEntity<Client>(updatedClient, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>("err", HttpStatus.BAD_REQUEST);
        }
    }

}

