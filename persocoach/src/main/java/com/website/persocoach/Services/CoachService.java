package com.website.persocoach.Services;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CoachService implements ICoachService{

    @Autowired
    CoachRepository repo;

    @Override
    public void save(Coach c) {
        repo.save(c);
    }

    @Override
    public int getNbCoaches() {
        return repo.findAll().size();

    }

    @Override
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    @Override
    public Coach findById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Page<Coach> findAll(Pageable page) {
        return repo.findAll(page);
    }

    @Override
    public Page<Coach> findByName(String name,Pageable page) {
       

        return  repo.findAll(page);
    }

    @Override
    public Page<Coach> findByGender(String g, Pageable page) {
        return null;
    }

    @Override
    public Page<Coach> findByType(String t, Pageable page) {
        return null;
    }

    @Override
    public Collection<Coach> findByType(String type) {
        return null;
    }

    @Override
    public Page<Coach> findByRate(int x, Pageable page) {
        return null;
    }


}
