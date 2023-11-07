package com.iyan.donapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.model.dto.UserRegistroDto;

import jakarta.annotation.PostConstruct;

@Service
public class InsertDataService {
	
	@Autowired
	private UserService usersService;
	@Autowired
	private ProductoService productoService;

	@PostConstruct
	public void init() {
		UserRegistroDto user1 = new UserRegistroDto("iyansl", "iyan@email.com", "iyan@email.com");
		usersService.saveUser(user1);
		
		UserRegistroDto user2 = new UserRegistroDto("prueba", "prueba@email.com", "prueba@email.com");
		usersService.saveUser(user2);
		
		UserRegistroDto user3 = new UserRegistroDto("user", "user@gmail.com", "user@gmail.com");
		usersService.saveUser(user3);
		
		ProductoDto p = new ProductoDto("Producto1", "DescripcionProducto", "Alta", "Ropa", "A domicilio", "Entrega", "Nuevo");
		productoService.saveProducto(p, usersService.getUserByEmail(user1.getEmail()));
		
		ProductoDto p1 = new ProductoDto("Producto2", "DescripcionProducto2", "Alta", "Ropa", "A domicilio", "Entrega", "Nuevo");
		productoService.saveProducto(p1, usersService.getUserByEmail(user2.getEmail()));
	}
}
