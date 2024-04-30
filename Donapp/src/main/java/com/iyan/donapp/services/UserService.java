package com.iyan.donapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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

	public String saveUserToBeVerified(UserRegistroDto dto) {
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()), dto.getRoles(),
				false);
		user.setDescripcion("¡Acabo de unirme a Donapp!");
		if (user.getRoles() == null)
			user.setRoles(Arrays.asList(new Rol("ROLE_USER")));
		byte[] img = obtenerDatosImagenPorDefecto("/static/img/usuarios/usuario.png");
		user.setFoto(img);
		userRepository.save(user);
		return user.getToken();
	}

	public User confirmUserByToken(String token) {
		User user = userRepository.findByToken(token);
		return user;
	}

	public User saveConfirmedUser(User user) {
		user.setActivado(true);
		return userRepository.save(user);
	}

	public User saveUser(UserRegistroDto dto) {
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()), dto.getRoles());
		user.setDescripcion("¡Acabo de unirme a Donapp!");
		if (user.getRoles() == null)
			user.setRoles(Arrays.asList(new Rol("ROLE_USER")));
		byte[] img = obtenerDatosImagenPorDefecto("/static/img/usuarios/usuario.png");
		user.setFoto(img);
		return userRepository.save(user);
	}

	public User saveUser(UserRegistroDto dto, String ruta, boolean activado) {
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()), dto.getRoles());
		user.setDescripcion("¡Acabo de unirme a Donapp!");
		if (user.getRoles() == null)
			user.setRoles(Arrays.asList(new Rol("ROLE_USER")));
		byte[] img = obtenerDatosImagenPorDefecto(ruta);
		user.setFoto(img);
		user.setActivado(activado);
		return userRepository.save(user);
	}
	
	public User saveAdmin(UserRegistroDto dto, String ruta, boolean activado) {
		User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()), dto.getRoles());
		user.setDescripcion("¡Acabo de unirme a Donapp!");
		if (user.getRoles() == null)
			user.setRoles(Arrays.asList(new Rol("ROLE_ADMIN")));
		byte[] img = obtenerDatosImagenPorDefecto(ruta);
		user.setFoto(img);
		user.setActivado(activado);
		return userRepository.save(user);
	}

	private byte[] obtenerDatosImagenPorDefecto(String ruta) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(ruta);
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
	public void cambiarDescripcion(String desc, String username) {
		User user = getUserByUsername(username);
		user.setDescripcion(desc);
		userRepository.save(user);
	}

	public void eliminarCuenta(String username) {
		User user = getUserByUsername(username);
		user.getRoles().clear();
		userRepository.delete(user);
	}

	public void eliminarCuentaPorId(Long id) {
		User user = getUserById(id);
		userRepository.delete(user);
	}

	public User getUserById(Long id) {
		User u = userRepository.findById(id).get();
		u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
		return u;
	}

	public List<User> getAllUsersExceptActive(String username) {
		List<User> users = userRepository.findAllExceptActive(username);
		List<User> usersNotAdmin = new ArrayList<>();
		for (User u : users) {
			boolean isAdmin = false;
			for (Rol r : u.getRoles()) {
				if (r.getNombre().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}
			if (!isAdmin) {
				u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
				usersNotAdmin.add(u);
			}
		}
		return usersNotAdmin;
	}

	public List<User> findByUsernameContainingIgnoreCaseAndIdNot(String username, Long id) {
		List<User> users = userRepository.findByUsernameContainingIgnoreCaseAndIdNot(username, id);
		List<User> usersNotAdmin = new ArrayList<>();

		for (User u : users) {
			boolean isAdmin = false;
			for (Rol r : u.getRoles()) {
				if (r.getNombre().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}
			if (!isAdmin) {
				u.setFotoEncoded(Base64.getEncoder().encodeToString(u.getFoto()));
				usersNotAdmin.add(u);
			}
		}
		return usersNotAdmin;
	}

	public void resetPassword(User user, String newPassword) {
		user.setPassword(passEncoder.encode(newPassword));
		user.setPasswordResetToken(null);
		userRepository.save(user);
	}

	public User getUserByPasswordResetToken(String token) {
		return userRepository.findByPasswordResetToken(token);
	}

	public String generatePasswordResetToken(User user) {
		String token = UUID.randomUUID().toString();
		user.setPasswordResetToken(token);
		userRepository.save(user);
		return token;
	}

	public void updateVisibilidadEmail(User user) {
		user.setEmailVisible(!user.isEmailVisible());
		userRepository.save(user);
	}

}
