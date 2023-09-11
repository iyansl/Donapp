package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductosController {

	@GetMapping("/mercado")
	public String mercado() {
		return "mercado";
	}
	
	@GetMapping("/publicados")
	public String publicados() {
		return "publicados";
	}
	
	@GetMapping("/adquiridos")
	public String adquiridos() {
		return "adquiridos";
	}
	
	@GetMapping("/producto")
	public String producto() {
		return "producto";
	}
	
}
