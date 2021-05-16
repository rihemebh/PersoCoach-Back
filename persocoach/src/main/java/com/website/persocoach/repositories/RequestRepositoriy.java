package com.website.persocoach.repositories;

import com.website.persocoach.Models.ProgramRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepositoriy extends MongoRepository<ProgramRequest , String>
  {
}
