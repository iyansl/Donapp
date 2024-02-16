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
	@Autowired
	private DenunciasService denunciasService;

	@PostConstruct
	public void init() {

		UserRegistroDto user1 = new UserRegistroDto("iyansl", "iyancm@gmail.com", "iyancm@gmail.com", Arrays.asList(new Rol("ROLE_USER")));
		usersService.saveUser(user1, "/static/img/usuarios/user1.jpg");

		UserRegistroDto user2 = new UserRegistroDto("prueba", "iyancppv@gmail.com", "iyancppv@gmail.com", Arrays.asList(new Rol("ROLE_USER")));
		usersService.saveUser(user2, "/static/img/usuarios/user2.jpg");

		UserRegistroDto user3 = new UserRegistroDto("user", "user@gmail.com", "user@gmail.com", Arrays.asList(new Rol("ROLE_USER")));
		usersService.saveUser(user3, "/static/img/usuarios/usuario.png");
		UserRegistroDto admin = new UserRegistroDto("admin", "admin@email.com", "admin@email.com", Arrays.asList(new Rol("ROLE_ADMIN")));
		usersService.saveUser(admin, "/static/img/usuarios/admin.jpg");
		

		ProductoDto p = new ProductoDto("Camiseta negra", "Camiseta nueva sin usar, fue un regalo", "Alta", "Ropa", "A domicilio", "Entrega rápida",
				"Nuevo");
		productoService.saveProducto(p, usersService.getUserByEmail(user2.getEmail()), "/static/img/productos/camiseta.jpg");

		ProductoDto p1 = new ProductoDto("Osito de peluche", "Sin usar, era un regalo", "Baja", "Juguetes", "En un punto", "Entrega",
				"Nuevo");
		productoService.saveProducto(p1, usersService.getUserByEmail(user2.getEmail()), "/static/img/productos/osito.jpg");
		
		ProductoDto p2 = new ProductoDto("Nevera antigua", "Bastante uso pero funciona perfectamente", "Baja", "Electrodomésticos", "En un punto", "Entrega",
				"Nuevo");
		productoService.saveProducto(p2, usersService.getUserByEmail(user2.getEmail()), "/static/img/productos/nevera.jpg");
		
		ProductoDto p3 = new ProductoDto("Sofá moderno", "Bastante uso pero nuevo", "Baja", "Muebles", "En un punto", "Entrega",
				"Nuevo");
		productoService.saveProducto(p3, usersService.getUserByEmail(user2.getEmail()), "/static/img/productos/sofa.jpg");
		
		ProductoDto p4 = new ProductoDto("Batidora sin marca", "Muy usada por toda la familia, funciona perfecta", "Baja", "Electrodomésticos", "En un punto", "Entrega",
				"Nuevo");
		productoService.saveProducto(p4, usersService.getUserByEmail(user2.getEmail()), "/static/img/productos/batidora.jpg");

		
		Conversacion c1 = new Conversacion(usersService.getUserByEmail(user1.getEmail()),
				usersService.getUserByEmail(user2.getEmail()));
		conversacionesService.save(c1);
		
		Mensaje m1 = new Mensaje(c1, usersService.getUserByEmail(user1.getEmail()), "Hola");
		c1.getMensajes().add(m1);
		mensajesService.save(m1);

		denunciasService.denunciarUsuario(usersService.getUserByEmail(user1.getEmail()), usersService.getUserByEmail(user2.getEmail()), "Este usuario es un grosero, no debería seguir aquí");
	}
}
