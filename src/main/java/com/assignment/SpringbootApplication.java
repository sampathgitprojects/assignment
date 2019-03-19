package com.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.assignment.model.User;
import com.assignment.repository.UserRepository;


/**
 * @author sampath
 *This is spring boot main class to start application
 *
 */
@SpringBootApplication
public class SpringbootApplication {
	
	private static Logger logger = LoggerFactory.getLogger(SpringbootApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	
	/**
	 * Save users to H2 DB for testing
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(UserRepository urepository) {
		return args -> {
			
			logger.info("static data adding to h2 databse automatically");
			// Create users with BCrypt encoded password (user/user, admin/admin)
			User user1 = new User("user","$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER","Sp16902");
			User user2 = new User("admin","$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN","pu00160");
			urepository.save(user1);
			urepository.save(user2); 
		};
	}
}
