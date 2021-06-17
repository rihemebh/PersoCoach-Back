package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.repositories.*;
import com.website.persocoach.security.jwt.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/catalog")
@CrossOrigin(origins = "http://localhost:3000")

public class CoachController {


    @Autowired
    PasswordEncoder encoder;
    CoachService repository;
    @Autowired
    CoachRepository repo;

    @Autowired
    RequestRepositoriy Reqrepo;

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientRepository clientRepo;
    @Autowired
    ReviewRepository ReviewRepo;
    @Autowired
    BriefProgramRepository brepo;
    @Autowired
    ProgramRepository repo1;


    Collection<Coach> coaches = new ArrayList<>();

    CoachController(CoachService repository) {
        super();
        this.repository = repository;

    }


    @RequestMapping(value = "/coaches", method = RequestMethod.GET)
    public Page<Coach> coaches(@RequestParam("key") Optional<String> key,
                               @RequestParam("type") Optional<String> type,
                               @RequestParam("rate") Optional<Integer> rate,
                               @RequestParam("gender") Optional<String> gender,
                               @RequestParam("sort") Optional<String> sort,
                               @RequestParam("direction") Optional<Integer> direction,
                               @RequestParam("page") Optional<Integer> page) {

        if (direction.orElse(0) == 1) {
            return repository.findByRateTypeGender(rate.orElse(5), type.orElse(""), gender.orElse(""),
                    key.orElse(""),
                    PageRequest.of(page.orElse(0), 9, Sort.by(sort.orElse("rate")).ascending()));
        } else {
            return repository.findByRateTypeGender(rate.orElse(5), type.orElse(""), gender.orElse(""),
                    key.orElse(""),
                    PageRequest.of(page.orElse(0), 9, Sort.by(sort.orElse("rate")).descending()));
        }

    }


    @RequestMapping(value ="/coach/{id}", method = RequestMethod.GET)
    public Optional<Coach> getCoach(@PathVariable String id) {

        return repo.findById(id);
    }

    @RequestMapping(value = "/coachesNb", method = RequestMethod.GET)
    public int getNbCoaches(@RequestParam("key") Optional<String> key,
                            @RequestParam("type") Optional<String> type,
                            @RequestParam("rate") Optional<Integer> rate,
                            @RequestParam("gender") Optional<String> gender) {

        return repository.getNbCoaches(key.orElse(""), rate.orElse(5), type.orElse(""),
                gender.orElse(""));
    }

    @RequestMapping(value = "/coach/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoach(@PathVariable String  id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }


public void update(Coach c ){

    List<ProgramRequest> progs = Reqrepo.getAllByCoach_Id(c.getId());
    BriefProgram bprogram;
    for (ProgramRequest prog: progs
    ) {
        prog.setCoach(c);

        Reqrepo.save(prog);
        bprogram = brepo.findByRequest(prog).orElse(null);
        if(bprogram != null){
            bprogram.setRequest(prog);
            brepo.save(bprogram);
        }


    }
    List<DetailedProgram> programs = repo1.findAllByCoach_Id(c.getId()).orElse(null);
    if (programs != null)
    {
        for (DetailedProgram prog: programs
        ) {
            prog.setCoach(c);
            repo1.save(prog);
        }
    }

    List<Review> reviews = ReviewRepo.findAllByCoach_Id(c.getId());

    for(Review review : reviews){
        review.setCoach(c);
        ReviewRepo.save(review);
    }

    repository.saveCoach(c);
    }


    @RequestMapping(value = "/coach/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coach> updateCoach(@PathVariable String id, @RequestBody Coach c) {
        Coach c1 = repo.findById(id).orElse(null);
        c.setRate(c1.getRate());
        c.setRoles(c1.getRoles());
        c.setPassword(encoder.encode(c.getPassword()));
     update(c);
        return ResponseEntity.ok().body(c);
    }

    @RequestMapping(value = "/coach/add", method = RequestMethod.PUT)
    public ResponseEntity<Coach> addCoach(Coach c) throws URISyntaxException {

        repository.saveCoach(c);
        return ResponseEntity.created(new URI("/coach/add" + c.getId())).body(c);
    }
/*********** Requests ***********/

@RequestMapping(value ="/coach/{id}/requests", method = RequestMethod.GET)
public List<ProgramRequest> getAllRequests(@PathVariable String id){
    Coach c =repo.findById(id).orElse(null);
    return   Reqrepo.getAllByCoach(c);
}

    @RequestMapping(value = "/coach/{id}", method = RequestMethod.PUT)
    public void saveRequest(@PathVariable  String id,
                            @RequestParam String gender,
                            @RequestParam String goal,
                            @RequestParam Integer age,
                            @RequestParam Double height,
                            @RequestParam Double weight,
                            @RequestParam String c,
                            @RequestParam String practice

    ) {

        Client client = clientRepo.findById(c).orElse(null);
        Coach coach = repo.findById(id).orElse(null);

        ProgramRequest prog =new ProgramRequest(coach,client,height,
                weight, practice, gender, age,goal,"pending");
        Reqrepo.save(prog);
    }


 /******* Reviews ********/


 @RequestMapping(value = "/coach/{id}/review", method = RequestMethod.PUT)
 public void saveReview(@PathVariable String  id,@RequestParam Optional<String> desc,@RequestParam int rate,
 @RequestParam String clientId
 ){

     Coach coach= repo.findById(id).orElse(null);
     List<Review> reviews = ReviewRepo.findAllByCoach(coach);

     double r=rate;
     try{
         if (reviews.size() > 0) {
             for (Review review : reviews) {
                 r += review.getRate();


             }
             if (r  % 5 == 0){
                 assert coach != null;
                 coach.setRate(5);
             }else{
                 assert coach != null;
                 coach.setRate( (int) ((r/(reviews.size() +1)) % 6));
             }

             for (Review review : reviews) {
                 review.getCoach().setRate(coach.getRate());
                 ReviewRepo.save(review);

             }
         }else{
             assert coach != null;
             coach.setRate(rate);
         }
     }catch(Exception e){
         System.out.println(coach);
         System.out.println(e);
     }

     assert coach != null;
     update(coach);

     Client client = clientRepository.findById(clientId).orElse(null);
     ReviewRepo.save(new Review(client,coach, desc.orElse(""),rate,new Date(System.currentTimeMillis())));

 }
    @RequestMapping(value = "/coach/{id}/review", method = RequestMethod.GET)
    public List<Review> getCoachesReview(@PathVariable String id){
        Optional<Coach> coach = repo.findById(id);

        return ReviewRepo.findAllByCoach(coach.orElse(null));
    }

    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public List<Review> getAllReview(@RequestParam String key){


        return ReviewRepo.findAllByKey(key);
    }

    @RequestMapping(value = "/coach/review/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Review> updateReview(@RequestBody Review  review){
        //Review review = ReviewRepo.findById(id).orElse(null);
        Review r = ReviewRepo.save(review);
        return ResponseEntity.ok().body(r);
    }

    @RequestMapping(value = "/coach/review/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReview(@PathVariable String  id) {
        ReviewRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
