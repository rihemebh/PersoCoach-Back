package com.website.persocoach.Controllers;

import com.github.javafaker.Faker;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Services.ICoachService;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/catalog")
public class CoachController {

    @Autowired
    private final ICoachService repository;
    Collection<Coach> coaches = new ArrayList<>();
    CoachController(ICoachService repository){
        super();
        this.repository = repository;
    }
    @RequestMapping(value="/coaches",method = RequestMethod.GET)
    public Page<Coach> coaches(Pageable page) {
//
//        Faker faker = new Faker();
//     ArrayList<String> acadamicExp = new ArrayList<>();
//      ArrayList<String> workExp = new ArrayList<>();
//     ArrayList<String> reviews = new ArrayList<>();
//
//
//            Coach c = new Coach();
//            c.setName(faker.name().fullName());
//            c.setType(faker.random().hex(5));
//            c.setId(faker.idNumber().toString());
//            c.setGender(faker.lorem().word());
//            c.setUrl(faker.internet().image().intern());
//            //c.setUrl("https://www.superprof.fr/images/annonces/professeur-home-coach-sportif-domicile-salle-bordeaux-merignac.jpg");
//            c.setDescription(faker.lorem().paragraph());
//            acadamicExp.add(faker.lorem().sentence());
//            acadamicExp.add(faker.lorem().sentence());
//            workExp.add(faker.lorem().sentence());
//            workExp.add(faker.lorem().sentence());
//            c.setRate(faker.number().numberBetween(0,5));
//            reviews.add(faker.lorem().sentence());
//
//            c.setWorkExp(workExp);
//            c.setAcadamicExp(acadamicExp);
//            c.setReviews(reviews);
//            repository.save(c);

        return repository.findAll(page);
    }


   @RequestMapping(value="/coach/{id}",method= RequestMethod.GET)
    public Coach getCoach(@PathVariable String id){

        return repository.findById(id);
   }
    @RequestMapping(value="/coachesNb",method= RequestMethod.GET)
    public int getNbCoaches(){

        return repository.getNbCoaches();
    }

  @RequestMapping(value="/coach/delete/{id}", method=RequestMethod.DELETE)
   public ResponseEntity<?> deleteCoach(@PathVariable String id){
      repository.deleteById(id);
       return ResponseEntity.ok().build();
   }

   @RequestMapping(value="/coach/update/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coach> updateCoach(@RequestBody Coach c){
        repository.save(c);
        return ResponseEntity.ok().body(c);
    }


    @RequestMapping(value="/coach/add", method=RequestMethod.PUT)
    public ResponseEntity<Coach>  addCoach(Coach c) throws URISyntaxException {
      repository.save(c);
        return ResponseEntity.created(new URI("/coach/add" +c.getId())).body(c);

    }


}
