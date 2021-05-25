package com.website.persocoach.security.jwt;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.RequestRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CoachService {

    @Autowired
    CoachRepository repo;
    @Autowired
    RequestRepositoriy Reqrepo;

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

    public void saveReq(String id, Client client){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client c;
        try{
            c = (Client) principal;
        }catch(Exception e ){

            c = new Client(principal.toString());
        }
        c= client;
      Coach coach = repo.findById(id).orElse(null);

        ProgramRequest prog =new ProgramRequest(coach,client,client.getHeight(),
               client.getWeight(), client.getPractice(), client.getGender(),
                client.getAge(),client.getDescription(), new File(client.getUrl()));
        Reqrepo.save(prog);
        System.out.println("Request saved" + prog);
    }


    public Page<Coach> findByRateTypeGender(int rate, String type , String gender, String key ,Pageable page){
        return repo.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key,page);
    }







}
