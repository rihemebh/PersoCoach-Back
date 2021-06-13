package com.website.persocoach.security.jwt;

import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    RequestRepository repo;


    public void addRequest(ProgramRequest request){

        repo.save(request);

    }
}
