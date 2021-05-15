package com.website.persocoach.Controllers;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.Models.Review;
import com.website.persocoach.repositories.ReviewRepository;
import com.website.persocoach.services.CoachService;
import com.website.persocoach.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/catalog")
public class CoachController {

    CoachService repository;

    RequestService service;
    @Autowired
    ReviewRepository ReviewRepo;

    Collection<Coach> coaches = new ArrayList<>();

    CoachController(CoachService repository, RequestService service) {
        super();
        this.repository = repository;
        this.service = service;
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

    @RequestMapping(value = "/coach/{id}", method = RequestMethod.GET)
    public Coach getCoach(@PathVariable String id) {

        return repository.findById(id);
    }
    @RequestMapping(value = "/coach/{id}", method = RequestMethod.PUT)
    public void saveRequest(@PathVariable String id,
                            @RequestParam Optional<String> gender,
                            @RequestParam String goal,
                            @RequestParam Optional<Integer> age,
                            @RequestParam Optional<Double> height,
                            @RequestParam Optional<Double> weight,
                            @RequestParam Optional<File> pic,
                            @RequestParam String practice
    ) {
        Coach coach = repository.findById(id);
        Client client;
        // = new Client();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            client = (Client) ((UserDetails)principal);
        } else { client = new Client();
            String username = principal.toString();
            System.out.println(username);
        }
        service.addRequest(new ProgramRequest(coach,client,height.orElse(client.getHeight()),
                weight.orElse(client.getWeight()),practice,gender.orElse(client.getGender()),
                age.orElse(client.getAge()),goal,pic.orElse(null)));
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
    public ResponseEntity<?> deleteCoach(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/coach/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coach> updateCoach(@RequestBody Coach c) {
        repository.saveCoach(c);
        return ResponseEntity.ok().body(c);
    }

    @RequestMapping(value = "/coach/add", method = RequestMethod.PUT)
    public ResponseEntity<Coach> addCoach(Coach c) throws URISyntaxException {

        repository.saveCoach(c);
        return ResponseEntity.created(new URI("/coach/add" + c.getId())).body(c);


    }


 /******* Reviews ********/


 @RequestMapping(value = "/coach/{id}/review", method = RequestMethod.PUT)
 public void saveReview(@PathVariable String id,@RequestParam Optional<String> desc,@RequestParam int rate){
     final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     Client client;
       /* if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken))
        {
            if(auth.getDetails() !=null)
                System.out.println(auth.getDetails().getClass());
            if( auth.getDetails() instanceof UserDetails)
            {
                System.out.println("UserDetails");
                 client = (Client) auth;
            }
            else
            {
                System.out.println("!UserDetails");
            }
        }*/
     if (!(auth instanceof AnonymousAuthenticationToken))
         client = (Client) auth ;
     client = new Client();
     ReviewRepo.save(new Review(client,repository.findById(id), desc.orElse(""), rate,new Date(System.currentTimeMillis())));
 }
    @RequestMapping(value = "/coach/{id}/review", method = RequestMethod.GET)
    public List<Review> getCoachesReview(@PathVariable String id){
        Coach coach = repository.findById(id);

        return ReviewRepo.findAllByCoach(coach);
    }

    @RequestMapping(value = "/coach/review/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Review> updateReview(@RequestBody Review  review){
        //Review review = ReviewRepo.findById(id).orElse(null);
        Review r = ReviewRepo.save(review);
        return ResponseEntity.ok().body(r);
    }

    @RequestMapping(value = "/coach/review/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReview(@PathVariable String id) {
        ReviewRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
