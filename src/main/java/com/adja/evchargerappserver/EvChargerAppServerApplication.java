package com.adja.evchargerappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class EvChargerAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvChargerAppServerApplication.class, args);
	}

	/*@Bean
	CommandLineRunner run(PersonService personService) {
		return args -> {
			personService.post(new Person("Talpos Norbert", "norbi", "1234", "asd@asd.com"));
			personService.post(new Person("Virág József Ádám", "edemsz", "1234", "asd@asd.com"));

			personService.addRoleToUser(1L, "role_user");
			personService.addRoleToUser(2L, "role_admin");
			personService.addRoleToUser(2L, "role_user");
		};
	}*/
}
