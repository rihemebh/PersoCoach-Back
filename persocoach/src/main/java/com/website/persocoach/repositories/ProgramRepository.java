package com.website.persocoach.repositories;

import com.website.persocoach.Models.Program;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramRepository extends MongoRepository<Program, String> {
}
