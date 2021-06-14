package com.website.persocoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class PersocoachApplication {


    public static void main(String[] args) {
        SpringApplication.run(PersocoachApplication.class, args);
    }

/*
   @Bean
    public CommandLineRunner init(
            CoachRepository repo,
            RoleRepository r,
            ClientRepository crep,
            RequestRepository reqrep,
            PasswordEncoder encoder
            ) {

        return args -> {
            Random rand = new Random();
            int randomNum = (int) (Math.random() * (2));
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
                c.setUsername(faker.name().firstName());
                c.setPassword("123");

                Set<Role> role = new HashSet<>();
                role.add(r.findByName(RoleType.ROLE_COACH).orElse(new Role(RoleType.ROLE_COACH)));
                c.setRoles(role);
                c.setName(faker.name().fullName());
                c.setType(types[randomNum]);
                c.setId(new ObjectId(faker.idNumber().toString()));
                c.setGender(genders[randomNum]);
                c.setUrl(faker.internet().image());
                c.setDescription(faker.lorem().paragraph());
                acadamicExp.add(faker.lorem().sentence());
                acadamicExp.add(faker.lorem().sentence());
                workExp.add(faker.lorem().sentence());
                workExp.add(faker.lorem().sentence());
                c.setRate(0);
                reviews.add(faker.lorem().sentence());
                c.setWorkExp(workExp);
                c.setAcadamicExp(acadamicExp);


                repo.save(c);

            Set<Role> role = new HashSet<>();
            role.add(r.findByName(RoleType.ROLE_COACH).orElse(new Role(RoleType.ROLE_COACH)));

            Coach coach = new Coach(
                    "coach@coach.com",
                    "supercoach",
                    "coach123",
                    role
                    );
            coach.setName("Mohamed Foulen");
            coach.setType("Sport");
            coach.setGender("Male");
            coach.setDescription("I can change your lifestyle");
            coach.setUrl("https://www.superprof.fr/images/annonces/professeur-home-coach-sportif-personnel-bruxelles-woluwe-fitnastic-alentours.jpg");

            //repo.save(coach);

            //Set<Role> role2 = new HashSet<>();
            //role2.add(r.findByName(RoleType.ROLE_CLIENT).orElse(new Role(RoleType.ROLE_CLIENT)));
            Client client = crep.findByUsername("test");

            ProgramRequest programRequest = reqrep.getById("60a6c41b45d438377181111c");

            ArrayList<ProgramRequest> list = new ArrayList<ProgramRequest>();
            list.add(programRequest);

            client.setPassword(encoder.encode("test123"));

            //reqrep.save(programRequest);
            crep.save(client);
        };
    }
    */

}
