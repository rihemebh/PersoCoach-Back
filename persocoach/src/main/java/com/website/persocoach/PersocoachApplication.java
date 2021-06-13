package com.website.persocoach;

import com.github.javafaker.Faker;
import com.website.persocoach.Models.*;
import com.website.persocoach.repositories.ClientRepository;
import com.website.persocoach.repositories.CoachRepository;
import com.website.persocoach.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication

public class PersocoachApplication {


    public static void main(String[] args) {
        SpringApplication.run(PersocoachApplication.class, args);
    }


   @Bean
    public CommandLineRunner init(CoachRepository repo, RoleRepository r, ClientRepository crepo
                                 ) {

        return args -> {
          //  Random rand = new Random();
            int randomNum = (int) (Math.random() * (2));
            String[] genders = new String[2];
            genders[0] = "Women";
            genders[1] = "Man";
            String[] types = new String[2];
            types[0] = "Sport";
            types[1] = "Nutrition";
            Faker faker = new Faker();
            ArrayList<experience> acadamicExp = new ArrayList<>();
            ArrayList<experience> workExp = new ArrayList<>();
            //ArrayList<String> reviews = new ArrayList<>();

                Coach c = new Coach();
                c.setUsername(faker.name().firstName());
                c.setPassword("123");

                Set<Role> role = new HashSet<>();
                role.add(r.findByName(RoleType.ROLE_COACH).orElse(new Role(RoleType.ROLE_COACH)));
                c.setRoles(role);
                c.setName(faker.name().fullName());
                c.setType(types[randomNum]);
                //c.setId(new ObjectId(faker.idNumber().toString()));
                c.setGender(genders[randomNum]);
                c.setUrl(faker.internet().avatar());
                c.setDescription(faker.lorem().paragraph());


                acadamicExp.add(new experience(faker.idNumber().toString(),faker.lorem().word(),faker.lorem().sentence()));
                workExp.add(new experience(faker.idNumber().toString(),faker.lorem().word(),faker.lorem().sentence()));

                c.setRate(0);
                c.setEmail( faker.internet().safeEmailAddress());
               // reviews.add(faker.lorem().sentence());
                c.setWorkExp(workExp);
                c.setAcadamicExp(acadamicExp);


                repo.save(c);
//
//             String name ;
//             Integer age;
//             String gender;
//             String url;
//             String description;
//
         /*    Client client = crepo.findByUsername("riheme");
             client.setName(faker.name().fullName());
             client.setAge(faker.number().numberBetween(10,80));
             client.setDescription(faker.lorem().paragraph());
             client.setGender("Women");
             client.setUrl(faker.internet().avatar());
             crepo.save(client);

*/
        };
    }
}
