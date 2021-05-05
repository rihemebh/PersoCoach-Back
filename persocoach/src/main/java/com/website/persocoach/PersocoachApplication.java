package com.website.persocoach;

import com.github.javafaker.Faker;
import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class PersocoachApplication {
    @Autowired
    CoachRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(PersocoachApplication.class, args);
    }

   @Bean
    public CommandLineRunner init(CoachRepository repo) {

        return args -> {

            String[] genders = new String[2];
            genders[0] = "Women";
            genders[1] = "Men";
            String[] types = new String[2];
            types[0] = "Sport";
            types[1] = "Nutrition";
            Faker faker = new Faker();
            ArrayList<String> acadamicExp = new ArrayList<>();
            ArrayList<String> workExp = new ArrayList<>();
            ArrayList<String> reviews = new ArrayList<>();

                Coach c = new Coach();
                c.setName(faker.name().fullName());
                c.setType(types[0]);
                c.setId(faker.idNumber().toString());
                c.setGender(genders[1]);
                c.setUrl(faker.internet().image());
                c.setDescription(faker.lorem().paragraph());
                acadamicExp.add(faker.lorem().sentence());
                acadamicExp.add(faker.lorem().sentence());
                workExp.add(faker.lorem().sentence());
                workExp.add(faker.lorem().sentence());
                c.setRate(5);
                reviews.add(faker.lorem().sentence());
                c.setWorkExp(workExp);
                c.setAcadamicExp(acadamicExp);

                repo.save(c);




        };
    }
}
