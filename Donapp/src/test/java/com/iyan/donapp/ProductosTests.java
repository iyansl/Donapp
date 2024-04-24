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
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;

@SpringBootTest
class ProductosTests {

	@Autowired
	private ProductoService ps;
	
	@Autowired
	private UserService us;
	
	@Test
    public void testCreacionProducto() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		ProductoDto producto = new ProductoDto("titulo", "subtitulo", "urgencia", "tipo", "formaEntrega", "descripcionEntrega", "estado", "provincia", "ubicacion");
		us.saveUser(usuarioDto);
		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		ps.saveProducto(producto, usuario);
		
		// Test
		List<Producto> p = ps.findAllByUserId(usuario.getId());
        assertEquals("titulo", p.get(0).getTitulo());
        assertEquals("subtitulo", p.get(0).getSubtitulo());
        assertEquals("urgencia", p.get(0).getUrgencia());
        assertEquals("tipo", p.get(0).getTipo());
        assertEquals("formaEntrega", p.get(0).getFormaEntrega());
        assertEquals("descripcionEntrega", p.get(0).getDescripcionEntrega());
        assertEquals("tipo", p.get(0).getTipo());
        assertEquals("estado", p.get(0).getEstado());
        assertEquals("provincia", p.get(0).getProvincia());
        assertEquals("ubicacion", p.get(0).getUbicacion());
        
        // Limpiar el test
        us.eliminarCuenta("usuario");
    }
	
	@Test
    public void testEliminarProducto() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		ProductoDto producto = new ProductoDto("titulo", "subtitulo", "urgencia", "tipo", "formaEntrega", "descripcionEntrega", "estado", "provincia", "ubicacion");
		us.saveUser(usuarioDto);
		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		ps.saveProducto(producto, usuario);
		
		// Test
		List<Producto> p = ps.findAllByUserId(usuario.getId());
		assertEquals(1, p.size());
		ps.deleteProductoById(p.get(0).getId());
		p = ps.findAllByUserId(usuario.getId());
        assertEquals(0, p.size());
        
        // Limpiar el test
        us.eliminarCuenta("usuario");
    }
	
	@Test
    public void testEditarDescripcionProducto() {
		// Inicializar prueba
		UserRegistroDto usuarioDto = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		ProductoDto producto = new ProductoDto("titulo", "subtitulo", "urgencia", "tipo", "formaEntrega", "descripcionEntrega", "estado", "provincia", "ubicacion");
		us.saveUser(usuarioDto);
		User usuario = us.getUserByEmail(usuarioDto.getEmail());
		ps.saveProducto(producto, usuario);
		
		// Test
		ProductoDto producto1 = new ProductoDto(null, "subtitulo1", null, null, null, null, null, null, null);
		List<Producto> p = ps.findAllByUserId(usuario.getId());
		ps.updateProducto(producto1, p.get(0).getId(), usuario);
		p = ps.findAllByUserId(usuario.getId());
        assertEquals(p.get(0).getSubtitulo(), "subtitulo1");
        
        // Limpiar el test
        ps.deleteProductoById(p.get(0).getId());
        us.eliminarCuenta("usuario");
    }

}
