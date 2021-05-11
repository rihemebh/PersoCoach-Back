package com.website.persocoach.controllers;


import com.website.persocoach.Models.Role;
import com.website.persocoach.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired RoleRepository roleRepository;

    @PostMapping("add")
    public ResponseEntity<?> addNewRole(@RequestBody Role role){
        roleRepository.save(role);
        return new ResponseEntity<Role>(role,HttpStatus.CREATED);
    }
}
