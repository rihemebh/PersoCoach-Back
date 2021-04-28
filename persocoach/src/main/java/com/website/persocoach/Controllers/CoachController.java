package com.website.persocoach.Controllers;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.Services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/catalog")
public class CoachController {


    private final CoachService repository;

    Collection<Coach> coaches = new ArrayList<>();
    CoachController(CoachService repository){
        super();
        this.repository = repository;
    }
    @RequestMapping(value="/coaches",method = RequestMethod.GET)
    public Page<Coach> coaches(@RequestParam("key") Optional<String> key,
                               @RequestParam("type") Optional<String> type ,
                               @RequestParam("rate") Optional<Integer> rate ,
                               @RequestParam("gender") Optional<String> gender ,
                               @RequestParam("sort") Optional<String> sort,
                               @RequestParam("direction") Optional<Integer> direction,
                               @RequestParam("page") Optional<Integer> page) {

//        String[] genders = new String[2];
//        genders[0] = "Women";
//        genders[1] = "Men";
//        String[] types = new String[2];
//        types[0] = "Sport";
//        types[1] = "Nutrition";
//        Faker faker = new Faker();
//        ArrayList<String> acadamicExp = new ArrayList<>();
//        ArrayList<String> workExp = new ArrayList<>();
//        ArrayList<String> reviews = new ArrayList<>();
//
//            Coach c = new Coach();
//            c.setName(faker.name().fullName());
//            c.setType(types[faker.number().numberBetween(0, 2)]);
//            c.setId(faker.idNumber().toString());
//            c.setGender(genders[faker.number().numberBetween(0, 2)]);
//            c.setUrl(faker.internet().image());
//            c.setDescription(faker.lorem().paragraph());
//            acadamicExp.add(faker.lorem().sentence());
//            acadamicExp.add(faker.lorem().sentence());
//            workExp.add(faker.lorem().sentence());
//            workExp.add(faker.lorem().sentence());
//            c.setRate(faker.number().numberBetween(0, 6));
//            reviews.add(faker.lorem().sentence());
//            c.setWorkExp(workExp);
//            c.setAcadamicExp(acadamicExp);
//            c.setReviews(reviews);
//            repository.save(c);
//        if (direction.orElse(0) == 1) {
//            return repository.findByName(name.orElse(""), PageRequest.of(page.orElse(0), 8,
//                    Sort.by(sort.orElse("rate")).ascending()));
//        } else {
//            return repository.findByName(name.orElse(""), PageRequest.of(page.orElse(0), 8,
//                    Sort.by(sort.orElse("rate")).descending()));
//
//     }


            if (direction.orElse(0) == 1) {
                return repository.findByRateTypeGender(rate.orElse(5), type.orElse(""), gender.orElse(""),
                        key.orElse(""), PageRequest.of(page.orElse(0), 8, Sort.by(sort.orElse("rate")).ascending()));
            } else {
                return repository.findByRateTypeGender(rate.orElse(5), type.orElse(""), gender.orElse(""),
                        key.orElse(""), PageRequest.of(page.orElse(0), 8, Sort.by(sort.orElse("rate")).descending()));
            }


    }



//    @RequestMapping(value="/coaches",method= RequestMethod.GET)
//    public Page<Coach> getCoachesByFilter(@RequestParam("rate") int rate, @RequestParam("gender") String gender,
//                                          @RequestParam("type") String type ,Pageable page) {
//       CoachFilter filter= new CoachFilter(rate,gender,type);
//        return repository.findAllByFilter(filter,page);
//    }

    @RequestMapping(value="/coach/{id}",method= RequestMethod.GET)
    public Coach getCoach(@PathVariable String id){

        return repository.findById(id);
   }
    @RequestMapping(value="/coachesNb",method= RequestMethod.GET)
    public int getNbCoaches(@RequestParam("key") Optional<String> key ,
                            @RequestParam("type") Optional<String> type ,
                            @RequestParam("rate") Optional<Integer> rate ,
                            @RequestParam("gender") Optional<String> gender ){

        return repository.getNbCoaches(key.orElse(""),rate.orElse(5), type.orElse(""), gender.orElse(""));
    }

  @RequestMapping(value="/coach/delete/{id}", method=RequestMethod.DELETE)
   public ResponseEntity<?> deleteCoach(@PathVariable String id){
      repository.deleteById(id);
       return ResponseEntity.ok().build();
   }

   @RequestMapping(value="/coach/update/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coach> updateCoach(@RequestBody Coach c){
        repository.saveCoach(c);
        return ResponseEntity.ok().body(c);
    }


    @RequestMapping(value="/coach/add", method=RequestMethod.PUT)
    public ResponseEntity<Coach>  addCoach(Coach c) throws URISyntaxException {
      repository.saveCoach(c);
        return ResponseEntity.created(new URI("/coach/add" +c.getId())).body(c);

    }


}
