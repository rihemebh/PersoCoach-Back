package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coach")
public class Coach extends User implements Serializable {


    @Field(value = "name")
    private String name;
    @Field(value = "type")
    private String type;
    @Field(value = "gender")
    private String gender;
    private String url;
    @Field(value = "desription")
    private String description;
    @Field(value = "acadamicExp")
    private ArrayList<experience> acadamicExp = new ArrayList<>();
    @Field(value = "workExp")
    private ArrayList<experience> workExp = new ArrayList<>();
    @Field(value = "rate")
    private int rate;

    public Coach(String email, String username, String password, Set<Role> roles){
        super(email,username,password,roles);
    }


}