package com.website.persocoach.repositories;

import com.github.javafaker.Bool;
import com.website.persocoach.Models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Admin findByUsername(String username);
}
