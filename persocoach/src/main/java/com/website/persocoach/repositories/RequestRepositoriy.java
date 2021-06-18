package com.website.persocoach.repositories;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.ProgramRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RequestRepositoriy extends MongoRepository<ProgramRequest , String>
  {
    ProgramRequest getById(String id);
    @Query("{'Coach' : ?0}")
    List<ProgramRequest> getAllByCoach(Coach c);
    List<ProgramRequest> getAllByClient(Client c);
    List<ProgramRequest> getAllByCoach_Id(String c);
    List<ProgramRequest> getProgramRequestsByClient(Client client);
    List<ProgramRequest> getProgramRequestsByClient_Id(String id);


    ProgramRequest findByClient_id(String id);
  }
