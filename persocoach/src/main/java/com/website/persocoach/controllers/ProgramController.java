package com.website.persocoach.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.website.persocoach.Models.*;
import com.website.persocoach.repositories.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProgramController {
 //   private static final Logger logger = (Logger) LoggerFactory.getLogger(ProgramController.class);
 @Autowired
 private GridFsOperations operations;

    @Autowired
    private GridFsTemplate gridFsTemplate;
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
    @RequestMapping(value ="/program/{id}/day", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    public ResponseEntity<DetailedProgram> uploadVideo(@PathVariable String id , @RequestPart("video") MultipartFile vid ,
                            @RequestParam int day,
                            @RequestParam int week,
                            @RequestParam int complexity,
                            @RequestParam String breakfast,
                            @RequestParam Optional<String> extra,
                            @RequestParam String lunch,
                            @RequestParam String dinner,
                            @RequestParam int qte,
                            @RequestParam String restriction,
                            @RequestParam String desc
                            ) throws IOException {
        DailyProgram dayprog = new DailyProgram();
      //  System.out.printf("File name '%s' uploaded successfully.", vid.getOriginalFilename());
        final String location = "C:\\Users\\rihem\\Desktop\\PersoCoach1\\PersoCoach-Front\\src\\assets\\videos\\";
         byte [] data = vid.getBytes();
        Path path = Paths.get(location+vid.getOriginalFilename());
        Files.write(path,data);
      //  System.out.println(vid);
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "video");
        metaData.put("title", vid.getOriginalFilename());
        ObjectId idFile= gridFsTemplate.store(
                vid.getInputStream(), vid.getName(), vid.getContentType(), metaData);
     //   GridFSFile file1 = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(idFile)));
        FileUploaded file = new FileUploaded();
        file.setFileId(idFile.toString());

        file.setName(vid.getOriginalFilename());
   //     file.setData(operations.getResource(file1).getInputStream());
        //   file.setData(new Binary(BsonBinarySubType.BINARY,vid.getBytes()));
      //  file.setName(vid.getOriginalFilename());
        file.setType(vid.getContentType());
        dayprog.setVideos(file);
        dayprog.setDay(day);
        dayprog.setWeek(week);
        dayprog.setBreakfast(breakfast);
        dayprog.setDinner(dinner);
        dayprog.setLunch(lunch);
        dayprog.setProgress(0.0);
        dayprog.setStatus("empty");
        if(extra.isPresent()) dayprog.setExtra(extra.orElse(""));
        dayprog.setRestrictions(restriction);
        dayprog.setWaterQuantity(qte);
        dayprog.setActivitydesritpion(desc);

        dayprog.setComplexity(complexity);

       DetailedProgram dp=  repo1.findById(id).orElse(null);

        ArrayList<DailyProgram> daily;
        assert dp != null;
        if(dp.getDailyprogram() == null)
           daily  = new ArrayList<>();
         else daily = dp.getDailyprogram();
        daily.add(dayprog);
        dp.setDailyprogram(daily);
        repo1.save(dp);
       return ResponseEntity.ok().body(dp);
    }
    @RequestMapping(value ="/program/{id}/day/progress", method = RequestMethod.GET)
    public void saveProgress(@PathVariable String id, @RequestParam Double progress, @RequestParam int day){

        DetailedProgram dp=  repo1.findById(id).orElse(null);
        assert dp != null;
        ArrayList<DailyProgram> daily;
        if(dp.getDailyprogram() != null){

            daily = dp.getDailyprogram();

           DailyProgram d=  daily.get(day-1);
           Double prog= progress;
           if(progress == 99) prog = 100.0;
           d.setProgress(prog);
           d.setStatus("completed");
           daily.set(day-1,d);
           dp.setDailyprogram(daily);
           repo1.save(dp);

        }




    }

    @GetMapping("/video/{id}")
    public InputStream getVidep(@PathVariable String id) throws Exception {

        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        FileUploaded video = new FileUploaded() ;
        assert file != null;
        assert file.getMetadata() != null;
        video.setName(file.getMetadata().get("title").toString());
        video.setData(operations.getResource(file).getInputStream());

        return video.getData();
        //FileCopyUtils.copy(video.getStream(), response.getOutputStream());
    }

}
