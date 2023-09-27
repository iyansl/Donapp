package com.iyan.donapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iyan.donapp.model.User;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;

@Controller
public class ProductosController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductoService productoService;

	@GetMapping("/mercado")
	public String mercado(Model model) {
		model.addAttribute("productos", productoService.getAllProductos());
		return "mercado";
	}
	
	@GetMapping("/publicados")
	public String publicados(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("productos", productoService.findAllByUserId(obtained.getId()));
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
