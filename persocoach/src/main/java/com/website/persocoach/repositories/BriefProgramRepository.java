package com.website.persocoach.repositories;

import com.website.persocoach.Models.BriefProgram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  BriefProgramRepository extends MongoRepository<BriefProgram, String> {
}
