package com.website.persocoach.Services;

import com.website.persocoach.Models.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ICoachService {
    void save(Coach c);
    int getNbCoaches();
    void deleteById(String id);
    Coach findById(String id);
    Page<Coach> findAll(Pageable page);
    public Page<Coach> findByName(String name,Pageable page);
    public Page<Coach> findByGender(String g,Pageable page);
    public Page<Coach> findByType(String t,Pageable page);
    Collection<Coach> findByType(String type);
    public Page<Coach> findByRate(int x,Pageable page);

}
