package com.website.persocoach.repositories;

import com.website.persocoach.Models.Coach;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CoachRepository extends MongoRepository<Coach,String> {
}
