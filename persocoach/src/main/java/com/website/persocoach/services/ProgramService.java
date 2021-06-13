package com.website.persocoach.services;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.DetailedProgram;
import com.website.persocoach.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {
    @Autowired
    ProgramRepository programRepository;

    public Optional<List<DetailedProgram>> findAllByCoach(Coach c){
        return programRepository.findAllByCoach(c);
    }

    public DetailedProgram save(DetailedProgram detailedProgram){ return programRepository.save(detailedProgram);}

    public void deleteById(String id){ programRepository.deleteById(id);}

    public Optional<DetailedProgram> findById(String id){ return programRepository.findById(id);}
}
