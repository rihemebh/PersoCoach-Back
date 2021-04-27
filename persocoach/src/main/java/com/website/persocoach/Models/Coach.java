package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coach")
public class Coach implements Serializable {

    @Id
    @Generated
    private String id;
    @Field(value = "name")
    private String name;
    @Field(value = "type")
    private String type;
    @Field(value = "gender")
    private String gender;
    private String url;
    @Field(value = "desriptio,")
    private String description;
    @Field(value = "acadamic_Exp")
    private ArrayList<String> acadamicExp = new ArrayList<>();
    @Field(value = "work_Exp")
    private ArrayList<String> workExp = new ArrayList<>();
    @Field(value = "rate")
    private int rate;
    @Field(value = "reviews")
    private ArrayList<String> reviews = new ArrayList<>();


}