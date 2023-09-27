package com.iyan.donapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;

@Controller
public class PublicarController {
	
	@Autowired
	private ProductoService  productoService;
	
	@Autowired
	private UserService  userService;

	@GetMapping("/publicar")
	public String publicar() {
		return "publicar";
	}
	
	@PostMapping("/publicar")
	public String publicar(@ModelAttribute("producto") ProductoDto dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		productoService.saveProducto(dto, obtained);
		return "redirect:/publicar?exito";
	}
}
