package com.website.persocoach.controllers;

import com.website.persocoach.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/program")
@CrossOrigin(origins = "http://localhost:3000")
public class ProgramController {

    @Autowired
    ProgramRepository repo;

}
