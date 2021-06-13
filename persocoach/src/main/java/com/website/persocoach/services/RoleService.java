package com.website.persocoach.services;

import com.website.persocoach.Models.Role;
import com.website.persocoach.Models.RoleType;
import com.website.persocoach.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired RoleRepository roleRepository;

    public Optional<Role> findByName(RoleType name){
        return roleRepository.findByName(name);
    }
    public Role save(Role role){ return roleRepository.save(role);}
}
