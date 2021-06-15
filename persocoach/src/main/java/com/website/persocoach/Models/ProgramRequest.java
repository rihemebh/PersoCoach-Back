package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Request")
public class ProgramRequest implements Serializable {
    @Id
    String id;
    Coach coach;
    Client client;
    Double height;
    Double weight;
    String practice;
    String Gender;
    Integer age;
    String goal;
    File url;
    String status;

    public ProgramRequest(Coach coach, Client client,
                          Double height, Double weight, String practice, String gender, Integer age,
                          String goal, File url,String status) {
        this.coach = coach;
        this.client = client;
        this.height = height;
        this.weight = weight;
        this.practice = practice;
        Gender = gender;
        this.age = age;
        this.goal = goal;
        this.url = url;
        this.status = status;
    }
    public ProgramRequest(Coach coach, Client client,
                          Double height, Double weight, String practice, String gender, Integer age,
                          String goal ,String status) {
        this.coach = coach;
        this.client = client;
        this.height = height;
        this.weight = weight;
        this.practice = practice;
        Gender = gender;
        this.age = age;
        this.goal = goal;
        this.status = status;
    }
}
