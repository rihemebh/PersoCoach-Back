package com.website.persocoach;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class PersocoachApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersocoachApplication.class, args);
	}

}
