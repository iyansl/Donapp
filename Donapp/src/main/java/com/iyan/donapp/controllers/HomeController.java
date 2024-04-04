package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String inicio() {
		return "home/inicio";
	}

	@GetMapping("/ayuda")
	public String ayuda() {
		return "home/ayuda";
	}

	@GetMapping("/conocenos")
	public String conocenos() {
		return "home/conocenos";
	}
	
	@GetMapping("/politicaPrivacidad")
	public String politicaPrivacidad() {
		return "home/politicaPrivacidad";
	}
	
	@GetMapping("/terminosCondiciones")
	public String terminosCondiciones() {
		return "home/terminosCondiciones";
	}
	
	@GetMapping("/contacto")
	public String contacto() {
		return "home/contacto";
	}

}
