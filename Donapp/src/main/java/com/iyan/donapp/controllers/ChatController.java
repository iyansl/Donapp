package com.iyan.donapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

	@GetMapping("/conversaciones")
	public String conversaciones() {
		return "conversaciones";
	}
	
	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
	
}
