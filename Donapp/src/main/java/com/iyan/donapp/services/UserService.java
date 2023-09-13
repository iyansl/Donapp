package com.iyan.donapp.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;	
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User saveUser(UserRegistroDto dto) {
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()), Arrays.asList(new Rol("ROL_USER")));
		return userRepository.save(user);
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Object getUserByUsername(String username) {
		return userRepository.finfByUsername(username);
	}
	
}
