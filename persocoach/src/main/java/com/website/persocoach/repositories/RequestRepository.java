package com.website.persocoach.repositories;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.ProgramRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestRepository extends MongoRepository<ProgramRequest , String>
{
    ProgramRequest getById(String id);
    List<ProgramRequest> getProgramRequestsByClient(Client client);
    List<ProgramRequest> getProgramRequestsByClient_Id(String id);
}
