package com.website.persocoach.Models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Experiences {

    private ArrayList<experience> acadamicExp = new ArrayList<>();

    private ArrayList<experience> workExp = new ArrayList<>();
}
