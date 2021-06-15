package com.website.persocoach.controllers;

import com.website.persocoach.Models.*;
import com.website.persocoach.repositories.*;
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

    @Autowired
    BriefProgramRepository repo;
    @Autowired
    ProgramRepository repo1;
    @Autowired
    RequestRepositoriy reqrepo;
    @Autowired
    CoachRepository crepo;
    @Autowired
    ClientRepository clientRepo;

/****************************Brief Program***************************/

    @RequestMapping(value = "/bprogram", method = RequestMethod.GET)
    public Page<BriefProgram> getall(Pageable page){

         return  repo.findAll(page);
    }

    @RequestMapping(value = "/bprogram/add/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BriefProgram> add(@RequestBody BriefProgram bp,@PathVariable String id) throws URISyntaxException {
        ProgramRequest pr = reqrepo.getById(id);
        pr.setStatus("Waiting for response");
        reqrepo.save(pr);
        bp.setRequest(pr);
        repo.save(bp);
        return  ResponseEntity.created(new URI("/api/bprogram/add/" + bp.getId())).body(bp);
    }

    @RequestMapping(value = "/bprogram/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletebprogram(@PathVariable String  id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/bprogram/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BriefProgram> updateprogram(@RequestBody BriefProgram bp) {
        repo.save(bp);
        return ResponseEntity.ok().body(bp);
    }

    @RequestMapping(value = "/bprogram/{id}/response", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BriefProgram> bprogramesp(@PathVariable String id,@RequestBody response resp) {
        ProgramRequest req = reqrepo.findById(id).orElse(null);
        BriefProgram prog  = repo.findByRequest(req).orElse(null);
        assert prog != null;
        assert req != null;
        req.setStatus(resp.getRep());
        reqrepo.save(req);
        prog.getRequest().setStatus(resp.getRep());
        repo.save(prog);
        return ResponseEntity.ok().body(prog);
    }

    @RequestMapping(value ="/bprogram/{id}", method = RequestMethod.GET)
    public Optional<BriefProgram> getBriefProgram(@PathVariable String id) {

        return repo.findById(id);
    }

    @RequestMapping(value ="/bprogram/request", method = RequestMethod.POST)
    public Optional<BriefProgram> getBriefProgramByRequest(@RequestBody ProgramRequest request) {
        return repo.findByRequest(request);
    }

    @RequestMapping(value ="program/{id}/bprogram", method = RequestMethod.POST)
    public Optional<BriefProgram> getBriefProgramfromProg(@PathVariable String id) {
       DetailedProgram p =  repo1.findById(id).orElse(null);
       Optional<BriefProgram> b = null;
if(p != null){
    ProgramRequest req = p.getRequest();

    b = repo.findByRequest(req);

}



        return b;
    }


    /****************************detailed Program***************************/

    @RequestMapping(value = "/programs/{id}", method = RequestMethod.GET)
    public List<DetailedProgram> getAll(@PathVariable String id){
        Coach c = crepo.findById(id).orElse(null);
        Client cl = clientRepo.findById(id).orElse(null);
        if(c != null)
        return  repo1.findAllByCoach(c).orElse(null);
        else
          return  repo1.findAllByClient(cl).orElse(null);
    }

    @RequestMapping(value = "/program/add", method = RequestMethod.PUT)
    public String addProgram(@RequestBody DetailedProgram p) throws URISyntaxException {
        ProgramRequest pr = reqrepo.getById(p.getRequest().getId());
        pr.setStatus("closed");
        reqrepo.save(pr);
        p.setRequest(pr);
        p.setStatus("In progress");
        repo1.save(p);
        return  p.getId();
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
        ArrayList<DailyProgram> daily;
        assert dp != null;
        if(dp.getDailyprogram() == null)
           daily  = new ArrayList<>();
         else daily = dp.getDailyprogram();
        daily.add(d);
        dp.setDailyprogram(daily);
        repo1.save(dp);
        return ResponseEntity.ok().body(dp);
    }
}
