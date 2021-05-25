package com.website.persocoach.controllers;

import com.website.persocoach.Models.BriefProgram;
import com.website.persocoach.Models.DailyProgram;
import com.website.persocoach.Models.DetailedProgram;
import com.website.persocoach.repositories.BriefProgramRepository;
import com.website.persocoach.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProgramController {

    @Autowired
    BriefProgramRepository repo;
    @Autowired
    ProgramRepository repo1;

/****************************Brief Program***************************/

    @RequestMapping(value = "/bprogram", method = RequestMethod.GET)
    public Page<BriefProgram> getall(Pageable page){

         return  repo.findAll(page);
    }

    @RequestMapping(value = "/bprogram/add", method = RequestMethod.PUT)
    public ResponseEntity<BriefProgram> add(@RequestBody BriefProgram bp) throws URISyntaxException {
        repo.save(bp);
        return  ResponseEntity.created(new URI("/api/bprogram/add/" + bp.getId())).body(bp);
    }

    @RequestMapping(value = "/bprogram/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoach(@PathVariable String  id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/bprogram/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BriefProgram> updateCoach(@RequestBody BriefProgram bp) {
        repo.save(bp);
        return ResponseEntity.ok().body(bp);
    }

    @RequestMapping(value ="/bprogram/{id}", method = RequestMethod.GET)
    public Optional<BriefProgram> getBriefProgram(@PathVariable String id) {

        return repo.findById(id);
    }



    /****************************detailed Program***************************/

    @RequestMapping(value = "/program", method = RequestMethod.GET)
    public Page<DetailedProgram> getallPrograms(Pageable page){

        return  repo1.findAll(page);
    }

    @RequestMapping(value = "/program/add", method = RequestMethod.PUT)
    public ResponseEntity<DetailedProgram> addProgram(@RequestBody DetailedProgram p) throws URISyntaxException {
        repo1.save(p);
        return  ResponseEntity.created(new URI("/api/program/add/" + p.getId())).body(p);
    }

    @RequestMapping(value = "/program/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProgram(@PathVariable String  id) {
        repo1.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/program/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedProgram> updateProgram(@RequestBody DetailedProgram p) {
        repo1.save(p);
        return ResponseEntity.ok().body(p);
    }

    @RequestMapping(value ="/program/{id}", method = RequestMethod.GET)
    public Optional<DetailedProgram> getProgram(@PathVariable String id) {

        return repo1.findById(id);
    }

    @RequestMapping(value ="/program/{id}/day", method = RequestMethod.PUT)
    public ResponseEntity<DetailedProgram> addDayProgram(@PathVariable String id,
    @RequestBody DailyProgram d) {
       DetailedProgram dp=  repo1.findById(id).orElse(null);
        ArrayList<DailyProgram> daily = dp.getDailyprogram();
        daily.add(d);
        dp.setDailyprogram(daily);
        repo1.save(dp);
        return ResponseEntity.ok().body(dp);
    }


}
