package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.services.BriefProgramService;
import com.website.persocoach.services.CoachesService;
import com.website.persocoach.services.ProgramService;
import com.website.persocoach.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProgramController {

    @Autowired BriefProgramService briefProgramService;
    @Autowired ProgramService programService;
    @Autowired RequestService requestService;
    @Autowired
    CoachesService coachesService;

/****************************Brief Program***************************/

    @RequestMapping(value = "/bprogram", method = RequestMethod.GET)
    public Page<BriefProgram> getall(Pageable page){
         return  briefProgramService.findAll(page);
    }

    @RequestMapping(value = "/bprogram/add/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BriefProgram> add(@RequestBody BriefProgram bp,@PathVariable String id) throws URISyntaxException {
        ProgramRequest pr = requestService.getById(id);
        pr.setStatus("accepted");
        requestService.save(pr);
        bp.setRequest(pr);
        briefProgramService.save(bp);
        return  ResponseEntity.created(new URI("/api/bprogram/add/" + bp.getId())).body(bp);
    }

    @RequestMapping(value = "/bprogram/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletebprogram(@PathVariable String  id) {
        programService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/bprogram/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BriefProgram> updateprogram(@RequestBody BriefProgram bp) {
        briefProgramService.save(bp);
        return ResponseEntity.ok().body(bp);
    }

    @RequestMapping(value = "/bprogram/{id}/response", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BriefProgram> bprogramesp(@PathVariable String id,@RequestBody String rep) {
        Optional<BriefProgram> prog  = Optional.ofNullable(briefProgramService.findById(id).orElse(null));
        prog.get().getRequest().setStatus(rep);
        briefProgramService.save(prog.get());
        return ResponseEntity.ok().body(prog.get());
    }

    @RequestMapping(value ="/bprogram/{id}", method = RequestMethod.GET)
    public Optional<BriefProgram> getBriefProgram(@PathVariable String id) {

        return briefProgramService.findById(id);
    }

    @RequestMapping(value ="/bprogram/request", method = RequestMethod.POST)
    public Optional<BriefProgram> getBriefProgramByRequest(@RequestBody ProgramRequest request) {
        return briefProgramService.findByRequest(request);
    }

    /****************************detailed Program***************************/

    @RequestMapping(value = "/programs/{id}", method = RequestMethod.GET)
    public List<DetailedProgram> getAll(@PathVariable String id){
        Coach c = coachesService.findById(id).orElse(null);
        return  programService.findAllByCoach(c).orElse(null);
    }

    @RequestMapping(value = "/program/add", method = RequestMethod.PUT)
    public String addProgram(@RequestBody DetailedProgram p) throws URISyntaxException {
        programService.save(p);
        return  p.getId();
    }

    @RequestMapping(value = "/program/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProgram(@PathVariable String  id) {
        programService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/program/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedProgram> updateProgram(@RequestBody DetailedProgram p) {
        programService.save(p);
        return ResponseEntity.ok().body(p);
    }

    @RequestMapping(value ="/program/{id}", method = RequestMethod.GET)
    public Optional<DetailedProgram> getProgram(@PathVariable String id) {
        return programService.findById(id);
    }

    @RequestMapping(value ="/program/{id}/day", method = RequestMethod.PUT)
    public ResponseEntity<DetailedProgram> addDayProgram(@PathVariable String id,
    @RequestBody DailyProgram d) {
       DetailedProgram dp=  programService.findById(id).orElse(null);
        ArrayList<DailyProgram> daily;
        assert dp != null;


        if(dp.getDailyprogram() == null)
           daily  = new ArrayList<>();
         else daily = dp.getDailyprogram();
        daily.add(d);
        dp.setDailyprogram(daily);
        programService.save(dp);
        return ResponseEntity.ok().body(dp);
    }
}
