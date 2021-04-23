package com.website.persocoach.Models;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
public class Client {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Client Email is required")
    @Column(unique = true)
    private String email;

    //@NotBlank(message = "Client username is required")
    public String username;

    @NotBlank(message = "Client password is required")
    @Size(min=6, max=10, message = "Please use 6 to 10 characters")
    public String password;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;



    public Client() {
    }

    public Client(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Client(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreated_At() {
        return created_At;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }


}
