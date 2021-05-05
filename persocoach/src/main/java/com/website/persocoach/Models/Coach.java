package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coach")
public class Coach implements Serializable {

    @MongoId
    @Generated
    private String id;
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