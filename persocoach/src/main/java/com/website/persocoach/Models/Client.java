package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client extends User implements Serializable {


    public Client(String email, String username, String password,Set<Role> roles){
        super(email,username,password,roles);
    }
//
//    public Client(String id, String email, String username, String password,Set<Role> roles){
//        super(id, email,username,password,roles);
//    }
//
//    public Client(String id, String email, String username, String password,Set<Role> roles){
//        super(id, email,username,password,roles);
//    }

    public Client(String email, String username, String password, Set<Role> roles, String name, String url, String description) {
        super(email, username, password, roles);
        this.name = name;
        this.url = url;
        this.description = description;
    }


    private String name;
    private Integer age;
    private String gender;
    private String url;
    private Double weight;
    private Double height;
    private String description;
    //private List<ProgramRequest> programRequestList;
    public Client(String name){
        this.name= name;
    }

}
