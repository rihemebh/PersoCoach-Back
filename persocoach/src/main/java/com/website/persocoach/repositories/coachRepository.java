package com.website.persocoach.repositories;

import com.website.persocoach.Models.coach;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface coachRepository extends MongoRepository<coach,String> {
}
