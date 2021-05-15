package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client implements Serializable {

    @Id
    @Generated
    private String id;
    @Field(value = "name")
    private String name;
    private Integer age;
    @Field(value = "gender")
    private String gender;
    private String url;
    private Double weight;
    private Double height;
    @Field(value = "desription")
    private String description;

}
