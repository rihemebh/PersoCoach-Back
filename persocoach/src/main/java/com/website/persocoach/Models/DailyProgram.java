package com.website.persocoach.Models;

import lombok.Data;

import java.io.File;
import java.io.Serializable;

@Data
public class DailyProgram implements Serializable {
    int day;
    int week;
    int complexity;
    String Breakfast;
    String lunch;
    String dinner;
    String extra;
    int WaterQuantity;
    String restrictions;
    File videos;
    String activitydesritpion;

}
