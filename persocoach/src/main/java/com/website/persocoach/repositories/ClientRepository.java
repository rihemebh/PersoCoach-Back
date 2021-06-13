package com.website.persocoach.repositories;

import com.website.persocoach.Models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {

    Client findByUsername(String username);
    List<Client> findAll();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    String getIdByUsername(String username);
    Client getClientById(String id);
    Optional<Client> findById(String id);
}
