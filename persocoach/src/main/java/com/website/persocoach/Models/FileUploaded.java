package com.website.persocoach.Models;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.InputStream;

@Data
public class FileUploaded {

    @Id
    String fileId;
    String name;
    String type;
    InputStream data;
}
