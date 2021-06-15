package com.website.persocoach.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "admin")
public class Admin extends User{
    public Admin(String email, String username, String password, Set<Role> roles){
        super(email,username,password,roles);
    }
}
