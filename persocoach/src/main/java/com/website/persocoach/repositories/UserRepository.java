package com.website.persocoach.repositories;

import com.website.persocoach.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
    List<User> findAll();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    String getIdByUsername(String username);
}
