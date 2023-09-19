package com.iyan.donapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	private UserRepository userRepository;	
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User saveUser(UserRegistroDto dto) {
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()), Arrays.asList(new Rol("ROL_USER")));
		user.setDescripcion("¡Acabo de unirme a Donapp!");
		byte[] img = obtenerDatosImagenPorDefecto();
		user.setFoto(img);
		return userRepository.save(user);
	}
	
	private byte[] obtenerDatosImagenPorDefecto() {
	    try {
	        InputStream inputStream = getClass().getResourceAsStream("/static/img/usuario.png");
	        if (inputStream != null) {
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, length);
	            }
	            return outputStream.toByteArray();
	        } else {
	            System.out.println("Imagen por defecto no encontrada");
	            return null;
	        }
	    } catch (IOException e) {
	        System.out.println("Error al cargar imagen por defecto");
	        return null;
	    }
	}


	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User getUserByUsername(String username) {
		return userRepository.finfByUsername(username);
	}

	@Transactional
	public void cambiarFoto(MultipartFile foto, String userN) {
		User user = getUserByUsername(userN);
		byte[] fotoBytes;
		try {
			fotoBytes = foto.getBytes();
			user.setFoto(fotoBytes);
			userRepository.save(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void cambiarEmail(String email, String username) {
		User user = getUserByUsername(username);
		User userExists = getUserByEmail(email);
		if (userExists == null) {
			user.setEmail(email);
			userRepository.save(user);
		}
	}
	
	@Transactional
	public void cambiarDescripcion(String desc, String username) {
		User user = getUserByUsername(username);
		user.setDescripcion(desc);
		userRepository.save(user);	
	}

	public void eliminarCuenta(String username) {
		User user = getUserByUsername(username);
		userRepository.delete(user);
	}
	
}
