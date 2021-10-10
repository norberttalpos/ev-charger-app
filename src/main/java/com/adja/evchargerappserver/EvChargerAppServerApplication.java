package com.adja.evchargerappserver;

import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
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

	public static void main(String[] args) {
		SpringApplication.run(EvChargerAppServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(PersonService personService) {
		return args -> {
			try {
				Person norbi=personService.getByUsername("norbi");
				norbi.setPassword("1234");
				personService.put(norbi.getId(),norbi);
			}
			catch(NotValidUpdateException e) {
				e.printStackTrace();
			}

			/*try {
				personService.put(personService.getByUsername("edemsz").getId(),new Person("Virág József Ádám", "edemsz", "1234", "asd@asd.com"));
			}
			catch(NotValidUpdateException e) {
				e.printStackTrace();
			}*/

			try {
				personService.addRoleToUser("edemsz", "role_user");
			}
			catch(NotValidUpdateException e) {
				e.printStackTrace();
			}

			try {
				personService.addRoleToUser("norbi", "role_admin");
			}
			catch(NotValidUpdateException e) {
				e.printStackTrace();
			}

			try {
				personService.addRoleToUser("norbi", "role_user");
			}
			catch(NotValidUpdateException e) {
				e.printStackTrace();
			}
		};
	}
}
