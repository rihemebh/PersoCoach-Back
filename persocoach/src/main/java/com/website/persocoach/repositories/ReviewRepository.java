package com.website.persocoach.repositories;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    @Query("{ 'coach': ?0}")
    List<Review> findAllByCoach(Coach coach);

    List<Review> findAllByClient_Id(String id);

    List<Review> findAllByCoach_Id(String id);

    @Query("{ $or: [ { 'id' : { $regex : new RegExp(?0,'i') } } , { 'text' : { $regex : new RegExp(?0,'i') } } ]}")
    List<Review> findAllByKey(String key);


}


