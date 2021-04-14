package com.website.persocoach.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class coach implements Serializable {
    @Id
    private int id;
    private String name;
    private String description;
    private ArrayList<String> acadamicExp = new ArrayList<>();
    private ArrayList<String> workExp = new ArrayList<>();
    private int rate;
    private ArrayList<String> reviews = new ArrayList<>();


}