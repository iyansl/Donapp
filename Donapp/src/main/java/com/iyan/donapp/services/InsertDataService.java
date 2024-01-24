package com.iyan.donapp.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Conversacion;
import com.iyan.donapp.model.Mensaje;
import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.model.dto.UserRegistroDto;

import jakarta.annotation.PostConstruct;

@Service
public class InsertDataService {

	@Autowired
	private UserService usersService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ConversacionesService conversacionesService;
	@Autowired
	private MensajesService mensajesService;

	@PostConstruct
	public void init() {

		UserRegistroDto user1 = new UserRegistroDto("iyansl", "iyan@email.com", "iyan@email.com", Arrays.asList(new Rol("ROLE_USER")));
		usersService.saveUser(user1);

		UserRegistroDto user2 = new UserRegistroDto("prueba", "prueba@email.com", "prueba@email.com", Arrays.asList(new Rol("ROLE_USER")));
		usersService.saveUser(user2);

		UserRegistroDto user3 = new UserRegistroDto("user", "user@gmail.com", "user@gmail.com", Arrays.asList(new Rol("ROLE_USER")));
		usersService.saveUser(user3);
		
		UserRegistroDto admin = new UserRegistroDto("admin", "admin@email.com", "admin@email.com", Arrays.asList(new Rol("ROLE_ADMIN")));
		usersService.saveUser(admin);

		ProductoDto p = new ProductoDto("Producto1", "DescripcionProducto", "Alta", "Ropa", "A domicilio", "Entrega",
				"Nuevo");
		productoService.saveProducto(p, usersService.getUserByEmail(user1.getEmail()));

		ProductoDto p1 = new ProductoDto("Producto2", "DescripcionProducto2", "Alta", "Ropa", "En un punto", "Entrega",
				"Nuevo");
		productoService.saveProducto(p1, usersService.getUserByEmail(user2.getEmail()));

		Conversacion c1 = new Conversacion(usersService.getUserByEmail(user1.getEmail()),
				usersService.getUserByEmail(user2.getEmail()));
		conversacionesService.save(c1);
		
		Mensaje m1 = new Mensaje(c1, usersService.getUserByEmail(user1.getEmail()), "Hola");
		c1.getMensajes().add(m1);
		mensajesService.save(m1);
		
	}
}
