package com.iyan.donapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DonappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonappApplication.class, args);
	}

}
