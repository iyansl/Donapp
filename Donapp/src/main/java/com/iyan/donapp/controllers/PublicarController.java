package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicarController {

	@GetMapping("/publicar")
	public String publicar() {
		return "publicar";
	}
}
