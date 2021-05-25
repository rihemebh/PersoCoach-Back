package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client")
public class Client extends User implements Serializable {



    private String name;
    private Integer age;
    private String gender;
    private String url; //pic
    private Double weight;
    private Double height;
    private String description;
    private String practice;
    public Client(String name){
        this.name= name;
    }

    public Client(Integer age, String gender,
                  String url, Double weight, Double height, String description, String practice) {
        this.age = age;
        this.gender = gender;
        this.url = url;
        this.weight = weight;
        this.height = height;
        this.description = description;
        this.practice = practice;
    }
}
