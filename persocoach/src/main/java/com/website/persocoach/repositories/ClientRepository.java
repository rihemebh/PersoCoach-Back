package com.website.persocoach.repositories;

import com.website.persocoach.Models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client,Long> {
    Client findByUsername(String username);
    Client findClientById(Long client_id);
}
