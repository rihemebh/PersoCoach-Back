package com.website.persocoach.repositories;

import com.website.persocoach.Models.BriefProgram;
import com.website.persocoach.Models.ProgramRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface  BriefProgramRepository extends MongoRepository<BriefProgram, String> {
    @Query("{ 'request' : ?0 }")
    Optional<BriefProgram> findByRequest(ProgramRequest request);
}
