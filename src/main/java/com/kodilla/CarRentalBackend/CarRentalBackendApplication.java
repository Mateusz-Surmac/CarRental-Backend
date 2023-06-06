package com.kodilla.CarRentalBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CarRentalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalBackendApplication.class, args);
	}

}
