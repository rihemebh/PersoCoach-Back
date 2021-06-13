package com.website.persocoach.services;

import com.website.persocoach.Models.Admin;
import com.website.persocoach.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired AdminRepository adminRepository;

    public Boolean existsByUsername(String username){
        return adminRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email){
        return adminRepository.existsByEmail(email);
    }

    public Admin findByUsername(String username){
        return adminRepository.findByUsername(username);
    }

    public Admin save(Admin admin){
        return adminRepository.save(admin);
    }

    public Optional<Admin> getById(String id){ return adminRepository.findById(id) ;}

}
