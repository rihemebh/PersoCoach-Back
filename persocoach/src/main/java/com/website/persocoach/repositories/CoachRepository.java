package com.website.persocoach.repositories;

import com.website.persocoach.Models.Coach;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoachRepository extends MongoRepository<Coach,String> {
}
