package com.website.persocoach.repositories;

import com.website.persocoach.Models.ProgramRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepositoriy extends MongoRepository<ProgramRequest , String> ,
        PagingAndSortingRepository<ProgramRequest , String> {
}
