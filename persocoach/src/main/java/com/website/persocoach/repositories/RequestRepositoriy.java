package com.website.persocoach.repositories;

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

}
