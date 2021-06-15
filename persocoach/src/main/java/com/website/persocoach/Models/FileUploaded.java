package com.website.persocoach.Models;


import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@Data
public class FileUploaded {

    @Id
    String fileId;
    String name;
    String type;
    Binary data;
}
