package com.website.persocoach.repositories;

import com.website.persocoach.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);
    List<User> findAll();
}
