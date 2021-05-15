package com.website.persocoach.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess(){
        return "public content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('CLIENT') or hasRole('COACH') or hasRole('ADMIN')")
    public String userAccess(){
        return "user content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "admin content";
    }


}
