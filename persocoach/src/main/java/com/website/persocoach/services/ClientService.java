package com.website.persocoach.services;

import com.website.persocoach.Models.Client;
import com.website.persocoach.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired ClientRepository clientRepository;

    public Client getByUsername(String username){
        return clientRepository.findByUsername(username);
    }

    public List<Client> getAll(){
        return clientRepository.findAll();
    }

    public Boolean existsByEmail(String email){
        return clientRepository.existsByEmail(email);
    }

    public Boolean existsByUsername(String username){
        return clientRepository.existsByUsername(username);
    }

    public String getIdByUsername(String username){
        return clientRepository.getIdByUsername(username);
    }

    public Client getClientById(String id){
        return clientRepository.getClientById(id);
    }

    public Optional<Client> findById(String id){
        return clientRepository.findById(id);
    }

    public Client save(Client client){ return clientRepository.save(client);}

}
