package com.website.persocoach.repositories;

import com.website.persocoach.Models.Role;
import com.website.persocoach.Models.RoleType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(RoleType name);
}
