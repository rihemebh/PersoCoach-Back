package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="review")
public class Review {
    @MongoId
    @Generated
    private String id;
    private Client client;
    private Coach coach;
    private String text;
    private int rate;
    private Date date;

    public Review(Client client, Coach coach, String desc, int rate, Date date) {
        this.client=client;
        this.coach=coach;
    this.text=desc;
    this.rate= rate;
    this.date=date;
    }
}
