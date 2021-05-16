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


    Coach coach;
    Client client;
    int complexity; //daily activity complexity
    ArrayList<DailyProgram> dailyprogram ; // size : duration*7


}
