package com.website.persocoach.controllers;

import com.website.persocoach.Models.Client;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.services.CoachService;
import com.website.persocoach.services.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/catalog")
@CrossOrigin(origins = "http://localhost:3000")

public class CoachController {

    private final CoachService repository;
    private final RequestService service;
    Collection<Coach> coaches = new ArrayList<>();

    CoachController(CoachService repository, RequestService service) {
        super();
        this.repository = repository;
        this.service = service;
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
        Client client = new Client();
        service.addRequest(new ProgramRequest(coach,client,height.orElse(client.getHeight()),
                weight.orElse(client.getWeight()),practice,gender.orElse(client.getGender()),
                age.orElse(client.getAge()),goal,pic.orElse(null)));
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
                    PageRequest.of(page.orElse(0), 8, Sort.by(sort.orElse("rate")).ascending()));
        } else {
            return repository.findByRateTypeGender(rate.orElse(5), type.orElse(""), gender.orElse(""),
                    key.orElse(""),
                    PageRequest.of(page.orElse(0), 8, Sort.by(sort.orElse("rate")).descending()));
        }

    }

    @RequestMapping(value = "/coach/{id}", method = RequestMethod.GET)
    public Coach getCoach(@PathVariable String id) {

        return repository.findById(id);
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


}
