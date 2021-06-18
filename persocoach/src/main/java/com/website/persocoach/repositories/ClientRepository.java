package com.website.persocoach.repositories;

import com.website.persocoach.Models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
    @Query("{ $or: [ { 'name' : { $regex : new RegExp(?0,'i') } } , { 'email' : { $regex : new RegExp(?0,'i') } }" +
            ", { 'username' : { $regex : new RegExp(?0,'i') } }" +
            " ]}")
    List<Client> findAllByKey(String key);
}
