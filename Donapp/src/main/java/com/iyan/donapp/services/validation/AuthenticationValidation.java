package com.iyan.donapp.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.UserService;

@Component
public class AuthenticationValidation {
	
	@Autowired
	private UserService userService;

	public boolean validarEmailRegistro(UserRegistroDto dto) {
		if (userService.getUserByEmail(dto.getEmail()) != null) {
			return false;
		}
		return true;
	}

	public boolean validarUsernameRegistro(UserRegistroDto dto) {
		if (userService.getUserByUsername(dto.getUsername()) != null) {
			return false;
		}
		return true;
	}

	public boolean validarPasswordsCoinciden(UserRegistroDto dto) {
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			return false;
		}
		return true;
	}
	
	public boolean validarPasswordRegistro(UserRegistroDto dto) {
	    String regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	    return dto.getPassword().matches(regex);
	}

	public boolean validarEmailFormatRegistro(UserRegistroDto dto) {
	    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    return dto.getEmail().matches(regex);
	}
	
	public boolean validarEmailFormatRegistro(String email) {
	    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    return email.matches(regex);
	}

	public boolean validarPasswordsCoinciden(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return false;
		}
		return true;
	}

	public boolean validarPasswordRegistro(String password) {
		String regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	    return password.matches(regex);
	}
}
