package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SolicitudesController {

	@GetMapping("/solicitudes")
	public String solicitudes() {
		return "solicitudes";
	}
}
