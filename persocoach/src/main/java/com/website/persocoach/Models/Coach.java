package com.website.persocoach.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor

@Data
@Document(collection = "coach")
public class Coach implements Serializable {

    public Coach(String id,String name, String type, String gender, String description, int rate) {
        this.id=id;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.description = description;
        this.rate = rate;
    }

    @Id
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAcadamicExp() {
        return acadamicExp;
    }

    public void setAcadamicExp(ArrayList<String> acadamicExp) {
        this.acadamicExp = acadamicExp;
    }

    public ArrayList<String> getWorkExp() {
        return workExp;
    }

    public void setWorkExp(ArrayList<String> workExp) {
        this.workExp = workExp;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }
}