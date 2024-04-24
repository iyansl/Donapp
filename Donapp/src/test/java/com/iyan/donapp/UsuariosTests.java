package com.iyan.donapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.UserService;

@SpringBootTest
class UsuariosTests {

	@Autowired
	private UserService us;
	
	@Test
    public void testCreacionUsuario() {
		// Inicializar prueba
		UserRegistroDto usuario = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuario);

		// Test
		User u = us.getUserByEmail("usuario@email.com");
        assertEquals("usuario", u.getUsername());
        assertEquals("usuario@email.com", u.getEmail());
        assertEquals("ROLE_USER", ((Rol)(u.getRoles().toArray()[0])).getNombre());
        
        // Limpiar el test
        us.eliminarCuenta("usuario");
    }
	
	@Test
    public void testRolesUsuario() {
		// Inicializar prueba
		UserRegistroDto usuario = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		UserRegistroDto admin = new UserRegistroDto("administrador", "administrador@email.com", "password", Arrays.asList(new Rol("ROLE_ADMIN")));
		us.saveUser(usuario);
		us.saveUser(admin);
		
		// Test
		User u = us.getUserByEmail("usuario@email.com");
		User a = us.getUserByEmail("administrador@email.com");
        assertEquals("ROLE_USER", ((Rol)(u.getRoles().toArray()[0])).getNombre());
        assertEquals("ROLE_ADMIN", ((Rol)(a.getRoles().toArray()[0])).getNombre());
        
        // Limpiar el test
        us.eliminarCuenta("usuario");
        us.eliminarCuenta("administrador");
    }
	
	@Test
    public void testEncriptadoPassword() {
		// Inicializar prueba
		UserRegistroDto usuario = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuario);

		// Test
		User u = us.getUserByEmail("usuario@email.com");
        assertNotEquals("password", u.getPassword());
        
        // Limpiar el test
        us.eliminarCuenta("usuario");
    }
	
	@Test
    public void testBorrarCuentaUsuario() {
		// Inicializar prueba
		UserRegistroDto usuario = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuario);
		
		// Test
		us.eliminarCuenta("usuario");
		assertEquals(us.getUserByEmail("usuario@email.com"), null);
    }
	
	@Test
    public void testEditarBiografiaUsuario() {
		// Inicializar prueba
		UserRegistroDto usuario = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuario);

		// Test
		us.cambiarDescripcion("Prueba", "usuario");
		System.out.println(us.getUserByEmail("usuario@email.com").getDescripcion());
		assertTrue(us.getUserByEmail("usuario@email.com").getDescripcion().equals("Prueba"));
		
		// Limpiar el test
		us.eliminarCuenta("usuario");
    }
	
	@Test
    public void testCambiarVisibilidadEmail() {
		// Inicializar prueba
		UserRegistroDto usuario = new UserRegistroDto("usuario", "usuario@email.com", "password", Arrays.asList(new Rol("ROLE_USER")));
		us.saveUser(usuario);

		// Test
		User u = us.getUserByEmail("usuario@email.com");
		assertEquals(u.isEmailVisible(), false);
		us.updateVisibilidadEmail(u);
		assertEquals(u.isEmailVisible(), true);
		
		// Limpiar el test
		us.eliminarCuenta("usuario");
    }
	
	

}
