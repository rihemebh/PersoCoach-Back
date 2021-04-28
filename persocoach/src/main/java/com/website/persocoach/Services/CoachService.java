package com.website.persocoach.Services;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.CoachFilter;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    CoachRepository repo;


    public void saveCoach(Coach c) {
        repo.save(c);
    }

    public int getNbCoaches(String key,int rate,String type,String gender) {

            return repo.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key).size();



    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }


    public Coach findById(String id) {
        return repo.findById(id).orElse(null);
    }


    public Page<Coach> findAll(Pageable page) {
        return repo.findAll(page);
    }


    public Page<Coach> findByNameOrType(String key, Pageable page) {

        return  repo.findAllByNameOrType(key,page);
    }


   public Page<Coach> findByRateTypeGender(int rate, String type , String gender, String key ,Pageable page){
        return repo.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key,page);
}

    public Page<Coach> findByRate(int x, Pageable page) {
        return null;
    }


    public Page<Coach> findAllByFilter(CoachFilter filter,Pageable page) {


        return null;
    }


}
