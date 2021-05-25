package com.website.persocoach.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public  class  Program {

    @Id
    String id;
    int duration; // number of weeks
    String frequency;//number of hour / days
    String type; // type of diet
    Coach coach;
    Client client;
}
