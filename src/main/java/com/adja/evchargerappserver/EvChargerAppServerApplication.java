package com.adja.evchargerappserver;

import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.person.Person;
import com.adja.evchargerappserver.api.person.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class EvChargerAppServerApplication {

	public static final boolean jwtEnabled = true;
	public static final boolean charging = true;
	public static final boolean sendMails = true;

	public static void main(String[] args) {
		SpringApplication.run(EvChargerAppServerApplication.class, args);
	}


	@Bean
	CommandLineRunner run(PersonService personService) {
		return args -> {
			personService.addInitialPerson("norbi","1234");
			personService.addInitialPerson("edemsz","woozyface");
			personService.addInitialPerson("admin","admin");
			personService.addInitialPerson("admin2","admin");
			personService.addInitialPerson("user","user");
			personService.addInitialPerson("user2","user");

			personService.setInitialRole("edemsz","role_user");
			personService.setInitialRole("norbi","role_admin");
			personService.setInitialRole("norbi","role_user");
			personService.setInitialRole("admin","role_admin");
			personService.setInitialRole("admin","role_user");
			personService.setInitialRole("admin2","role_admin");
			personService.setInitialRole("admin2","role_user");
			personService.setInitialRole("user","role_user");
			personService.setInitialRole("user2","role_user");








		};
	}
}
