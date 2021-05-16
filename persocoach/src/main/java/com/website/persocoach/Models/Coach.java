package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;


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
    private ArrayList<String> acadamicExp = new ArrayList<>();
    @Field(value = "workExp")
    private ArrayList<String> workExp = new ArrayList<>();
    @Field(value = "rate")
    private int rate;


}