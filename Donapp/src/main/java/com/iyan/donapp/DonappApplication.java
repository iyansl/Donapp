package com.iyan.donapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class DonappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonappApplication.class, args);
	}

}
