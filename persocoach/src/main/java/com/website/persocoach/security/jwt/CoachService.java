package com.website.persocoach.services;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    }


    public Coach findById(String id) {
        return repo.findById(id).orElse(null);
    }


    public Page<Coach> findAll(Pageable page) {
        return repo.findAll(page);
    }


 /*   public Page<Coach> findByNameOrType(String key, Pageable page) {

        return  repo.findAllByNameOrType(key,page);
    }*/


   public Page<Coach> findByRateTypeGender(int rate, String type , String gender, String key ,Pageable page){
        return repo.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key,page);
}







}
