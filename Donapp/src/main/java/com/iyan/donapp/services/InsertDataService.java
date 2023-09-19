package com.iyan.donapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.dto.UserRegistroDto;

import jakarta.annotation.PostConstruct;

@Service
public class InsertDataService {
	
	@Autowired
	private UserService usersService;

	@PostConstruct
	public void init() {
		UserRegistroDto user1 = new UserRegistroDto("iyansl", "iyan@email.com", "iyan@email.com");
		usersService.saveUser(user1);

	}
}
