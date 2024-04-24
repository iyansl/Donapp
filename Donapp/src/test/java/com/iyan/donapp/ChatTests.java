package com.iyan.donapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iyan.donapp.model.Conversacion;
import com.iyan.donapp.model.Mensaje;
import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.ConversacionesService;
import com.iyan.donapp.services.MensajesService;
import com.iyan.donapp.services.UserService;

@SpringBootTest
class ChatTests {

	@Autowired
	private ConversacionesService ds;
	
	@Autowired
	private MensajesService ms;

	@Autowired
	private UserService us;

	@Test
	public void testMensajes() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuarioDto);
		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		UserRegistroDto usuario1Dto = new UserRegistroDto("usuario1", "usuario1@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuario1Dto);
		User usuario1 = us.getUserByEmail(usuario1Dto.getEmail());

		// Test
		Conversacion c1 = new Conversacion(usuario, usuario1);
		ds.save(c1);
		Mensaje m1 = new Mensaje(c1, usuario, "hola");
		ms.save(m1);
		ds.actualizarMensajes(c1);
		assertEquals(c1.getMensajes().size(), 1);
		
		// Limpiar el test
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}
	

}
