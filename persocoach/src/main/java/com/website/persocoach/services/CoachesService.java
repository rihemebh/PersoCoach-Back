package com.website.persocoach.services;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachesService {
    @Autowired CoachRepository coachRepository;


    public Optional<Coach> findById(String id){ return coachRepository.findById(id); }

    public Coach findByUsername(String name){
        return coachRepository.findByUsername(name);
    }

    public Optional<Coach> findWorExp(String id){
        return coachRepository.findWorkExp(id);
    }

    public Page<Coach> findAllByNameOrType(String key, Pageable page){
        return coachRepository.findAllByNameOrType(key, page);
    }

    public Page<Coach> findAllByRateTOrTypeOrGenderOrName(int rate, String type, String gender, String key, Pageable page){
        return coachRepository.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key,page);
    }

    public List<Coach> findAllByRateTOrTypeOrGenderOrName(int rate, String type, String gender, String key){
        return coachRepository.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key);
    }

    public Boolean existsByUsername(String name){
        return coachRepository.existsByUsername(name);
    }
    Boolean existsByEmail(String email){
        return coachRepository.existsByEmail(email);
    }

    public Coach save(Coach coach){
        return coachRepository.save(coach);
    }

    public void deleteById(String id){
        coachRepository.deleteById(id);
    }

}
