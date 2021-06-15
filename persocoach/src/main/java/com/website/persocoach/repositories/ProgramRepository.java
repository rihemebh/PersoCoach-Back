package com.website.persocoach.repositories;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.DetailedProgram;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends MongoRepository<DetailedProgram, String> {
    @Query("{ 'coach' : ?0 }")
    Optional<List<DetailedProgram>> findAllByCoach(Coach c);
    @Query("{ 'client' : ?0 }")
    Optional<List<DetailedProgram>> findAllByClient(Client c);
    Optional<List<DetailedProgram>> findAllByCoach_Id(String c);

    List<DetailedProgram> findAllByClient_Id(String id);
}

