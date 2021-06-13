package com.website.persocoach.services;

import com.website.persocoach.Models.BriefProgram;
import com.website.persocoach.Models.ProgramRequest;
import com.website.persocoach.repositories.BriefProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BriefProgramService {
    @Autowired BriefProgramRepository briefProgramRepository;

    public Optional<BriefProgram> findByRequest(ProgramRequest programRequest){
        return briefProgramRepository.findByRequest(programRequest);
    }

    public Page<BriefProgram> findAll(Pageable page){return briefProgramRepository.findAll(page);}
    public Optional<BriefProgram> findById(String id){ return briefProgramRepository.findById(id);}
    public BriefProgram save(BriefProgram briefProgram){ return briefProgramRepository.save(briefProgram);}
}
