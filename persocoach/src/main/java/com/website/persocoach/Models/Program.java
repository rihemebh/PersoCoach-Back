package com.website.persocoach.Models;

import org.springframework.data.annotation.Id;

public class Program {

    @Id
    String id;
    int duration; // number of weeks
    String frequency; //number of hour / days
}
