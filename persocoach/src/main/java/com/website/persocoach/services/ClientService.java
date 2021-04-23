package com.website.persocoach.services;


import com.website.persocoach.Models.Client;
import com.website.persocoach.exceptions.ClientNotFoundException;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;


    public Client getClientByUsername(String username) throws ClientNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if(client==null){
            throw new ClientNotFoundException("Client does not exist");
        }
        return client;
    }

    public Client getClientById(Long client_id){
        Client client = clientRepository.findClientById(client_id);
        if(client == null){
            throw new ClientNotFoundException("Client does not exist");
        }
        return client;
     }

     public Iterable<Client> getAll(){
        return clientRepository.findAll();
     }

    public Client save(Client client) {
        try{
            return clientRepository.save(client);
        }catch(Exception e){
            throw new ClientNotFoundException("client '"+client+"' already exists");
        }
    }

    public void deleteClientById(Long client_id){
        Client client = getClientById(client_id);
        if(client == null){
            throw new ClientNotFoundException("client does not exist");
        }
        clientRepository.deleteById(client_id);
    }

}
