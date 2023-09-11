package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/iniciarsesion")
	public String iniciarsesion() {
		return "iniciarsesion";
	}
	
	@GetMapping("/registrarse")
	public String registrarse() {
		return "registrarse";
	}
	
	@GetMapping("/usuario")
	public String usuario() {
		return "usuario";
	}
	
	@GetMapping("/miPerfil")
	public String miPerfil() {
		return "miPerfil";
	}
	
	@GetMapping("/buscarUsuarios")
	public String buscarUsuarios() {
		return "buscarUsuarios";
	}
	
}
