package com.iyan.donapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.model.dto.SolicitudDto;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.SolicitudService;
import com.iyan.donapp.services.UserService;

@SpringBootTest
class SolicitudesTest {

	@Autowired
	private ProductoService ps;

	@Autowired
	private UserService us;

	@Autowired
	private SolicitudService ss;

	@Test
	public void testEnviarSolicitud() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		UserRegistroDto usuario1Dto = new UserRegistroDto("usuario1", "usuario1@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		ProductoDto producto = new ProductoDto("titulo", "subtitulo", "urgencia", "tipo", "formaEntrega",
				"descripcionEntrega", "estado", "provincia", "ubicacion");
		us.saveUser(usuarioDto);
		us.saveUser(usuario1Dto);

		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		User usuario1 = us.getUserByEmail(usuario1Dto.getEmail());
		ps.saveProducto(producto, usuario);
		List<Producto> p = ps.findAllByUserId(usuario.getId());

		SolicitudDto solicitudDto = new SolicitudDto(usuario1, usuario, ps.getProductoById(p.get(0).getId()));
		ss.saveSolicitud(solicitudDto);

		// Test
		assertEquals(ss.getSolicitudesEnviadasByUserId(usuario1.getId()).size(), 1);
		assertEquals(ss.getSolicitudesRecibidasByUserId(usuario.getId()).size(), 1);

		// Limpiar el test
		ps.deleteProductoById(p.get(0).getId());
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}

	@Test
	public void testAceptarSolicitud() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		UserRegistroDto usuario1Dto = new UserRegistroDto("usuario1", "usuario1@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		ProductoDto producto = new ProductoDto("titulo", "subtitulo", "urgencia", "tipo", "formaEntrega",
				"descripcionEntrega", "estado", "provincia", "ubicacion");
		us.saveUser(usuarioDto);
		us.saveUser(usuario1Dto);

		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		User usuario1 = us.getUserByEmail(usuario1Dto.getEmail());
		ps.saveProducto(producto, usuario);
		List<Producto> p = ps.findAllByUserId(usuario.getId());

		SolicitudDto solicitudDto = new SolicitudDto(usuario1, usuario, ps.getProductoById(p.get(0).getId()));
		ss.saveSolicitud(solicitudDto);
		ss.aceptarSolicitud(ss.getSolicitudesEnviadasByUserId(usuario1.getId()).get(0), usuario1);
		
		// Test
		assertEquals(ss.getSolicitudesEnviadasByUserId(usuario1.getId()).size(), 0);
		assertEquals(ss.getSolicitudesRecibidasByUserId(usuario.getId()).size(), 0);
		
		// Limpiar el test
		ps.deleteProductoById(p.get(0).getId());
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}
	
	@Test
	public void testCancelarSolicitud() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		UserRegistroDto usuario1Dto = new UserRegistroDto("usuario1", "usuario1@email.com", "password",
				Arrays.asList(new Rol("ROLE_USER")));
		ProductoDto producto = new ProductoDto("titulo", "subtitulo", "urgencia", "tipo", "formaEntrega",
				"descripcionEntrega", "estado", "provincia", "ubicacion");
		us.saveUser(usuarioDto);
		us.saveUser(usuario1Dto);

		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		User usuario1 = us.getUserByEmail(usuario1Dto.getEmail());
		ps.saveProducto(producto, usuario);
		List<Producto> p = ps.findAllByUserId(usuario.getId());

		SolicitudDto solicitudDto = new SolicitudDto(usuario1, usuario, ps.getProductoById(p.get(0).getId()));
		ss.saveSolicitud(solicitudDto);
		ss.cancelarSolicitud(ss.getSolicitudesEnviadasByUserId(usuario1.getId()).get(0), usuario1);
		
		// Test
		assertEquals(ss.getSolicitudesEnviadasByUserId(usuario1.getId()).size(), 0);
		assertEquals(ss.getSolicitudesRecibidasByUserId(usuario.getId()).size(), 0);
		
		// Limpiar el test
		ps.deleteProductoById(p.get(0).getId());
		us.eliminarCuenta("usuario");
		us.eliminarCuenta("usuario1");
	}

}
