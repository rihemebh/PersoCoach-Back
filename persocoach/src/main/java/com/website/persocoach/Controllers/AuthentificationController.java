package com.website.persocoach.Controllers;


import com.website.persocoach.Models.Client;
import com.website.persocoach.services.ClientService;
import com.website.persocoach.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthentificationController {

    @Autowired
    ClientService clientService;

    @Autowired
    ValidationErrorService validationErrorService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> createNewClient(@Valid @RequestBody Client client, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.MapValidationErrorService(result);
        if(errorMap != null){
            return errorMap;
        }
        Client client1 = clientService.save(client);
        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }
}
