package com.iyan.donapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()),
				Arrays.asList(new Rol("ROL_USER")));
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
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	public User getUserByEmail(String email) {
		User u = userRepository.findByEmail(email);
		if (u != null)
			u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
		return u;
	}

	public User getUserByUsername(String username) {
		User u = userRepository.findByUsername(username);
		if (u != null) {
			u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
			u.updateFotoEncoded();
		}
		return u;
	}

	@Transactional
	public void cambiarFoto(MultipartFile foto, String userN) {
		User user = getUserByUsername(userN);
		byte[] fotoBytes;
		try {
			fotoBytes = foto.getBytes();
			user.setFoto(fotoBytes);
			user.setFotoEncoded(Base64.getEncoder().encodeToString(fotoBytes));
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

	public User getUserById(Long id) {
		User u = userRepository.findById(id).get();
		u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
		return u;
	}

	public List<User> getAllUsersExceptActive(String username) {
		List<User> users = userRepository.findAllExceptActive(username);
		for (User u: users)
			u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
		return users;
	}

}
