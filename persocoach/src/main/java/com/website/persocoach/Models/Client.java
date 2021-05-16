package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client extends User implements Serializable {


    @Id
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String url;
    private Double weight;
    private Double height;
    private String description;
    public Client(String name){
        this.name= name;
    }
}
