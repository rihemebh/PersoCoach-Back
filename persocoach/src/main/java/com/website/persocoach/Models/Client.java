package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client extends User implements Serializable {

    private String name;
    private Integer age;
    private String gender;
    private String url;
    private String description;


    public Client(String email, String username, String password,Set<Role> roles){
        super(email,username,password,roles);
    }


    public Client(String name){
        this.name= name;
    }


}
