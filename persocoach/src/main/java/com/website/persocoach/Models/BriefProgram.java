package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "briefprogram")
public class BriefProgram implements Serializable  {
    @Id
    String id;
    int duration; // number of weeks
    String frequency;//number of hour / days
    String type;
    String description ;
    ProgramRequest request;
}
