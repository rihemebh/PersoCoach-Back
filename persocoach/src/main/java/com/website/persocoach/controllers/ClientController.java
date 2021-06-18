package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.repositories.*;
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
import java.util.List;
import java.util.Optional;
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
    @Autowired private RequestRepositoriy requestRepository;
    @Autowired
    ReviewRepository ReviewRepo;
    @Autowired
    BriefProgramRepository brepo;
    @Autowired
    ProgramRepository repo1;



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
    @GetMapping("/clients")
    public List<Client> getClients(@RequestParam String key){



        return clientRepository.findAllByKey(key);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public void DeletetClient(@PathVariable String id){
         clientRepository.deleteById(id);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public ResponseEntity<?> updateClientInfo(@RequestParam String id,
                                              @RequestParam Optional<String> name,
                                              @RequestParam Optional<String> description,
                                              @RequestParam Optional<String> pwd){
        try {
            Client client1 = clientRepository.getClientById(id);

            if (client1== null){
                return new ResponseEntity<String>("error updating", HttpStatus.BAD_REQUEST);
            }
            String n = name.orElse(null);
            if(n != null ){
                client1.setName(n);

            }
            String desc = description.orElse(null);
            if(desc != null){
                client1.setDescription(desc);
            }
            String pass = pwd.orElse(null);
            if(pass != null ){
                client1.setPassword(passwordEncoder.encode(pass));
            }
            ProgramRequest request = requestRepository.findByClient_id(client1.getId());
            List<BriefProgram> bprograms = brepo.findAllByRequest(request);
            request.setClient(client1);
            requestRepository.save(request);
            for(BriefProgram bprog : bprograms){
                bprog.setRequest(request);
                brepo.save(bprog);
            }


            List<DetailedProgram> program = repo1.findAllByClient_Id(client1.getId());
            for(DetailedProgram prog : program){
                prog.setClient(client1);
                prog.setRequest(request);
                repo1.save(prog);
            }

            List<Review> reviews = ReviewRepo.findAllByClient_Id(client1.getId());

            for(Review review : reviews){
                review.setClient(client1);
                ReviewRepo.save(review);
            }




            Client updatedClient = clientRepository.save(client1);
            return new ResponseEntity<Client>(updatedClient, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>("err", HttpStatus.BAD_REQUEST);
        }
    }

    /******* Requests *********/
    @RequestMapping(value ="/{id}/requests", method = RequestMethod.GET)
    public List<ProgramRequest> getAllRequests(@PathVariable String id){
        Client c =clientRepository.findById(id).orElse(null);
        return   requestRepository.getAllByClient(c);
    }

}

