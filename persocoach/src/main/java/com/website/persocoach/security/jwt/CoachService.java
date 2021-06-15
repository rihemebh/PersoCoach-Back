package com.website.persocoach.security.jwt;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.RequestRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoachService {

    @Autowired
    CoachRepository repo;
    @Autowired
    RequestRepositoriy Reqrepo;
    @Autowired
    ClientRepository clientRepo;

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

//    public void saveReq(String id, Client c){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Client client;
//        System.out.println( ((UserDetails)principal).getUsername());
//        try{
//
//            client =  clientRepo.findByUsername(((UserDetails)principal).getUsername());
//
//
//        }catch(Exception e ){
//
//            client = new Client(principal.toString());
//        }
//     //   client=c;
//      Coach coach = repo.findById(id).orElse(null);
//
//        ProgramRequest prog =new ProgramRequest(coach,client,client.getHeight(),
//               client.getWeight(), client.getPractice(), client.getGender(),
//                client.getAge(),client.getDescription());
//        Reqrepo.save(prog);
//        System.out.println("Request saved" + prog);
//    }


    public Page<Coach> findByRateTypeGender(int rate, String type , String gender, String key ,Pageable page){
        return repo.findAllByRateTOrTypeOrGenderOrName(rate,type,gender,key,page);
    }







}
