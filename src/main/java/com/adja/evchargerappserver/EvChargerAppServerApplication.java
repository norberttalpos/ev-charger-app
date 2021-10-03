package com.adja.evchargerappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EvChargerAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvChargerAppServerApplication.class, args);
	}

}
