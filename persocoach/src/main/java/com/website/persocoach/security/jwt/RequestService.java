package com.website.persocoach.services;

import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.repositories.RequestRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    RequestRepositoriy repo;


    public void addRequest(ProgramRequest request){

        repo.save(request);

    }
}
