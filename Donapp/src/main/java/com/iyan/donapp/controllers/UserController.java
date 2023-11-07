package com.iyan.donapp.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iyan.donapp.model.User;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductoService productosService;

	@GetMapping("/iniciarsesion")
	public String iniciarsesion() {
		return "iniciarsesion";
	}

	@GetMapping("/usuario/{id}")
	public String usuario(@PathVariable Long id, Model model) {
		User u = userService.getUserById(id);
		model.addAttribute("usuario", u);
		model.addAttribute("productos", productosService.findAllByUserId(id));
		model.addAttribute("foto", Base64.getEncoder().encodeToString(u.getFoto()));
		return "usuario";
	}

	@GetMapping("/miPerfil")
	public String miPerfil(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("username", obtained.getUsername());
		model.addAttribute("email", obtained.getEmail());
		model.addAttribute("descripcion", obtained.getDescripcion());
		model.addAttribute("foto", Base64.getEncoder().encodeToString(obtained.getFoto()));
		return "miPerfil";
	}

	@GetMapping("/buscarUsuarios")
	public String buscarUsuarios(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("usuarios", userService.getAllUsersExceptActive(obtained.getUsername()));
		return "buscarUsuarios";
	}

	@PostMapping("/subirFoto")
	public String subirFoto(@RequestParam("foto") MultipartFile foto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userN = auth.getName();
		if (foto.getSize() != 0) {
			if (foto.getSize() > 3000000)
				return "redirect:/miPerfil?error";
			else
				userService.cambiarFoto(foto, userN);
		}
		return "redirect:/miPerfil";
	}
	
	@PostMapping("/cambiarEmail")
	public String cambiarEmail(String email) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		if (!email.contains("@") || !email.contains("."))
			return "redirect:/miPerfil?errorEmailInvalido";
		else if (userService.getUserByEmail(email) != null)
			return "redirect:/miPerfil?errorEmailUsado";
		userService.cambiarEmail(email, username);
		return "redirect:/miPerfil";
	}

	@PostMapping("/cambiarDescripcion")
	public String cambiarDescripcion(String descripcion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		if (descripcion.length() < 5)
			return "redirect:/miPerfil?descripcionCorta";
		if (descripcion.length() > 300)
			return "redirect:/miPerfil?descripcionLarga";
		userService.cambiarDescripcion(descripcion, username);
		return "redirect:/miPerfil";
	}
	
	 @GetMapping("/eliminarCuenta")
	    public String eliminarCuentaYLogout(HttpServletRequest request, HttpServletResponse response, Model model) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String username = auth.getName();
	        userService.eliminarCuenta(username);
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	        return "redirect:/confirmacionEliminacion";
	    }

}
