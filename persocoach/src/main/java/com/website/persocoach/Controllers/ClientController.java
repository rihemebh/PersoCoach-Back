package com.website.persocoach.Controllers;


import com.website.persocoach.Models.Client;
import com.website.persocoach.services.ClientService;
import com.website.persocoach.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ValidationErrorService validationErrorService;

    @GetMapping("/id/{client_id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long client_id){
        Client client = clientService.getClientById(client_id);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Client> getClientByUsername(@PathVariable String username){
        Client client = clientService.getClientByUsername(username);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Client> getAllClients(){
        return clientService.getAll();
    }

    @PostMapping("")
    public ResponseEntity<?> createNewClient(@Valid @RequestBody Client client, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.MapValidationErrorService(result);
        if (errorMap != null){
            return errorMap;
        }
        Client client1 = clientService.save(client);
        return new ResponseEntity<Client>(client1, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{client_id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long client_id){
        clientService.deleteClientById(client_id);
        return new ResponseEntity<String>("Client with id '"+client_id+"' deleted successfully", HttpStatus.OK);
    }





}
