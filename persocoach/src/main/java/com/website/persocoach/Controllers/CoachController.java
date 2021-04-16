package com.website.persocoach.Controllers;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/")
public class CoachController {

    @Autowired
    private final CoachRepository repository;

    CoachController (CoachRepository repo){
        super();
        this.repository=repo;
    }
    @RequestMapping(value="/coaches",method = RequestMethod.GET)
    public Collection<Coach> coaches() {
        return repository.findAll();
    }
    @RequestMapping(value="/coach/{id}",method= RequestMethod.GET)
    public Coach getCoach(@PathVariable int id){
        return repository.findById(Integer.toString(id)).orElse(null);
   }

   @RequestMapping(value="/coach/delete/{id}", method=RequestMethod.DELETE)
   public ResponseEntity<?> deleteCoach(@PathVariable int id){
      repository.deleteById(Integer.toString(id));
       return ResponseEntity.ok().build();
   }

    @RequestMapping(value="/coach/update/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Coach>  updateCoach(@RequestBody Coach c){

       Coach co=  repository.save(c);
        return ResponseEntity.ok().body(co);
    }


    @RequestMapping(value="/coach/add", method=RequestMethod.PUT)
    public ResponseEntity<Coach>  addCoach( Coach c) throws URISyntaxException {
        Coach co=  repository.save(c);
        return ResponseEntity.created(new URI("/coach/add" + co.getId())).body(co);

    }


}
