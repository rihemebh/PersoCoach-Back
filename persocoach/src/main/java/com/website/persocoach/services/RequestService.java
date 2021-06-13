package com.website.persocoach.services;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.repositories.RequestRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    @Autowired RequestRepositoriy requestRepositoriy;

    public ProgramRequest getById(String id){
        return requestRepositoriy.getById(id);
    }

    public List<ProgramRequest> getAllByCoach(Coach c){
        return requestRepositoriy.getAllByCoach(c);
    }

    public List<ProgramRequest> getProgramRequestsByClient_Id(String id){
        return requestRepositoriy.getProgramRequestsByClient_Id(id);
    }

    public ProgramRequest save(ProgramRequest programRequest){ return requestRepositoriy.save(programRequest);}
}
