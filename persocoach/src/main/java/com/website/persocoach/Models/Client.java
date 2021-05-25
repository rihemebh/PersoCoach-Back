package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client extends User implements Serializable {

    private String name;
    private Integer age;
    private String gender;
    private String url;
    private Double weight;
    private Double height;
    private String description;
    private String practice;

    public Client(String email, String username, String password,Set<Role> roles){
        super(email,username,password,roles);
    }

    public Client(String email, String username, String password, Set<Role> roles, String name, String url, String description) {
        super(email, username, password, roles);
        this.name = name;
        this.url = url;
        this.description = description;
    }

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
