package com.iyan.donapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iyan.donapp.model.Denuncia;
import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.DenunciasService;
import com.iyan.donapp.services.UserService;

@SpringBootTest
class DenunciasTests {

	@Autowired
	private DenunciasService ds;

	@Autowired
	private UserService us;

	@Test
	public void testDenunciarUsuario() {
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
		assertTrue(ds.denunciarUsuario(usuario, usuario1, "denunciaPrueba1234512345"));
		List<Denuncia> denuncias = ds.getAllDenuncias();
		Denuncia denunciaEncontrada = null;
		for (Denuncia denuncia : denuncias) {
			if (denuncia.getContenido().equals("denunciaPrueba1234512345")) {
				denunciaEncontrada = denuncia;
				break;
			}
		}
		assertEquals(denunciaEncontrada.getContenido(), "denunciaPrueba1234512345");
		
		// Limpiar el test
		ds.cerrarDenuncia(denunciaEncontrada);
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}
	
	@Test
	public void testCerrarDenuncia() {
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
		assertTrue(ds.denunciarUsuario(usuario, usuario1, "denunciaPrueba1234512345"));
		List<Denuncia> denuncias = ds.getAllDenuncias();
		Denuncia denunciaEncontrada = null;
		for (Denuncia denuncia : denuncias) {
			if (denuncia.getContenido().equals("denunciaPrueba1234512345")) {
				denunciaEncontrada = denuncia;
				break;
			}
		}
		assertEquals(denunciaEncontrada.getContenido(), "denunciaPrueba1234512345");
		
		ds.cerrarDenuncia(denunciaEncontrada);
		
		denuncias = ds.getAllDenuncias();
		denunciaEncontrada = null;
		for (Denuncia denuncia : denuncias) {
			if (denuncia.getContenido().equals("denunciaPrueba1234512345")) {
				denunciaEncontrada = denuncia;
				break;
			}
		}
		assertEquals(denunciaEncontrada, null);
		
		// Limpiar el test
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}

}
