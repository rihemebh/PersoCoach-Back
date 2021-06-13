package com.website.persocoach.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class experience  {
   @Id


    private String id;
    private String name;
    private String description;

}
