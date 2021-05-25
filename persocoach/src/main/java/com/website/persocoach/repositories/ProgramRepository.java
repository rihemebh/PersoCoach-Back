package com.website.persocoach.repositories;

import com.website.persocoach.Models.DetailedProgram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramRepository extends MongoRepository<DetailedProgram, String> {
}
