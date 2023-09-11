package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String inicio() {
		return "inicio";
	}
	
	@GetMapping("/ayuda")
	public String ayuda() {
		return "ayuda";
	}
	
	@GetMapping("/conocenos")
	public String conocenos() {
		return "conocenos";
	}

}
