package com.website.persocoach.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class Client implements Serializable {
    @Id
    private int id;
    private String name;
    private String age;
    private String description;
   private String email;
   private String password;
   private double weight;
    private double height;
    private String level;



}