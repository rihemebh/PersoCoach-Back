package com.website.persocoach.repositories;

import com.website.persocoach.Models.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@Repository
public interface CoachRepository extends MongoRepository<Coach,String>, PagingAndSortingRepository<Coach,String> {



}
