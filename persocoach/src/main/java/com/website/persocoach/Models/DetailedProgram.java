package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "detailedprogram")
public class DetailedProgram extends Program implements Serializable {



   //daily activity complexity
    String name;
    ArrayList<DailyProgram> dailyprogram ; // size : duration*7
    ProgramRequest request;

}
