package com.website.persocoach.Models;

import lombok.Data;

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
    FileUploaded videos;
    String activitydesritpion;
    Double progress;
    String status;

}
