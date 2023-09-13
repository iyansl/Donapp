package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.UserService;

@Controller
@RequestMapping("/registrarse")
public class AutenticationController {

	private UserService userService;
	
	public AutenticationController(UserService us) {
		super();
		this.userService = us;
	}
	
	@ModelAttribute("user")
	public UserRegistroDto nuevoUserRegistroDto() {
		return new UserRegistroDto();
	}

	@PostMapping
	public String registrarse(@ModelAttribute("user") UserRegistroDto dto) {
		if (userService.getUserByEmail(dto.getEmail()) != null) 
			return "redirect:/registrarse?error1";
		else if (userService.getUserByUsername(dto.getUsername()) != null) 
			return "redirect:/registrarse?error";
		else 
			userService.saveUser(dto);
		return "redirect:/iniciarsesion?exito";
	}
	
	@GetMapping
	public String mostrarRegistro() {
		return "/registrarse";
	}

}
