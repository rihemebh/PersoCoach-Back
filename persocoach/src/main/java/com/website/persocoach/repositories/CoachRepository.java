package com.website.persocoach.repositories;

import com.website.persocoach.Models.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CoachRepository extends MongoRepository<Coach, String>, PagingAndSortingRepository<Coach, String> {

    @Query("{ $or: [ { 'name' : /?0/ } , { 'type' : /?0/ } ] }")
    Page<Coach> findAllByNameOrType(String key, Pageable page);

   /* @Query("{ $or: [ { 'name' : /?0/ } , { 'type' : /?0/ } ] }")
    List<Coach> findAllByNameOrType(String key);*/

    @Query("{'rate' : { $lte : ?0 }  , 'gender': /^?2/ , 'type' : /?1/  ," +
            "$or: [ { 'name' : { $regex : new RegExp(?3,'i') } } , { 'description' : { $regex : new RegExp(?3,'i') } } ]  }")
    Page<Coach> findAllByRateTOrTypeOrGenderOrName(int rate, String type, String gender, String key, Pageable page);
    @Query("{'rate' : { $lte : ?0 }  , 'gender': /^?2/ , 'type' : /?1/ ," +
            "$or: [ { 'name' : { $regex : new RegExp(?3,'i') } } , { 'description' : { $regex : new RegExp(?3,'i') } } ]  }")
    List<Coach> findAllByRateTOrTypeOrGenderOrName(int rate, String type, String gender,String key);

  //  Page<Coach> findAllByType(String type, Pageable page);

}
