package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "briefprogram")
public class BriefProgram extends Program implements Serializable  {


    Coach coach;
    Client client;
    String type;
    String description ;
}
