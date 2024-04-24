package com.iyan.donapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.Valoracion;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.UserService;
import com.iyan.donapp.services.ValoracionesService;

@SpringBootTest
class ValoracionesTests {

	@Autowired
	private ValoracionesService vs;

	@Autowired
	private UserService us;

	@Test
	public void testValoracionValida() {
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
		vs.valorarUsuario(usuario, usuario1, "valoracion", 2);
		List<Valoracion> a = vs.getAllValoraciones();
		Valoracion valoracionEsperada = null;
		for (Valoracion v: a) {
			if (v.getUsuarioValorado().getEmail().equals("usuario1@email.com")) {
				valoracionEsperada = v;
				break;
			}	
		}
		assertEquals(valoracionEsperada.getUsuarioValorado().getEmail(), "usuario1@email.com");
		// Limpiar el test
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}
	
	@Test
	public void testValoracionInvalida() {
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
		vs.valorarUsuario(usuario, usuario1, "valoracion", 2);
		assertTrue(vs.usuarioYaValorado(usuario, usuario1));

		// Limpiar el test
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}
	

}
