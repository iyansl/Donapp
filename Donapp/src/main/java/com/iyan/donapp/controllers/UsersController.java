package com.iyan.donapp.controllers;

import java.util.Base64;
import java.util.List;

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
import com.iyan.donapp.model.Valoracion;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;
import com.iyan.donapp.services.ValoracionesService;
import com.iyan.donapp.services.email.EmailSender;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductoService productosService;
	
	@Autowired
	private EmailSender emailService;
	
	@Autowired
	private ValoracionesService valoracionesService;

	@GetMapping("/iniciarsesion")
	public String iniciarsesion() {
		return "iniciarsesion";
	}

	@GetMapping("/usuario/{id}")
	public String usuario(@PathVariable Long id, Model model) {
		User u = userService.getUserById(id);
		List<Valoracion> valoraciones = valoracionesService.findAllByUserId(id);
	    
		double media = 0.0;
	    if (!valoraciones.isEmpty()) {
	        int totalPuntuaciones = 0;
	        for (Valoracion valoracion : valoraciones) {
	            totalPuntuaciones += valoracion.getPuntuacion();
	        }
	        media = (double) totalPuntuaciones / valoraciones.size();
	        media = Math.round(media);
	    }
	    
		model.addAttribute("usuario", u);
		model.addAttribute("productos", productosService.findAllByUserId(id));
		model.addAttribute("valoraciones", valoraciones);
		model.addAttribute("mediaValoraciones", media);
		model.addAttribute("numValoraciones", valoraciones.size());
		model.addAttribute("foto", Base64.getEncoder().encodeToString(u.getFoto()));
		return "usuario";
	}
	
	@PostMapping("/valorarUsuario/{id}")
	public String valorarUsuario(@PathVariable Long id, @RequestParam("valoracionUsuario") String valoracionUsuario, @RequestParam("puntuacion") int puntuacion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User valorador = userService.getUserByUsername(username);
		User valorado = userService.getUserById(id);
		if (valoracionUsuario.isBlank() || valoracionUsuario.isEmpty() || valoracionUsuario.length()<5)
			return "redirect:/usuario/"+id+"?valoracionErronea";
		if (valoracionesService.usuarioYaValorado(valorador, valorado))
			return "redirect:/usuario/"+id+"?valoracionYaRealizada";
		valoracionesService.valorarUsuario(valorador, valorado, valoracionUsuario, puntuacion);
		return "redirect:/usuario/"+id;
	}

	@GetMapping("/miPerfil")
	public String miPerfil(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User obtained = userService.getUserByUsername(username);
		model.addAttribute("username", obtained.getUsername());
		model.addAttribute("email", obtained.getEmail());
		model.addAttribute("descripcion", obtained.getDescripcion());
		model.addAttribute("foto", Base64.getEncoder().encodeToString(obtained.getFoto()));
		model.addAttribute("notificaciones", obtained.getSolicitudesRecibidas().size());
		model.addAttribute("emailVisible", obtained.isEmailVisible());
		return "miPerfil";
	}
	
	@GetMapping("/ocultarMostrarEmail")
	public String ocultarMostrarEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		userService.updateVisibilidadEmail(obtained);
		return "redirect:/miPerfil";
	}

	@GetMapping("/buscarUsuarios")
	public String buscarUsuarios(@RequestParam(name = "nombre", required = false) String nombre, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);

		List<User> usuarios;
		if (nombre != null && !nombre.isEmpty()) {
			usuarios = userService.findByUsernameContainingIgnoreCaseAndIdNot(nombre, obtained.getId());
		} else {
			usuarios = userService.getAllUsersExceptActive(obtained.getUsername());
		}

		model.addAttribute("usuarios", usuarios);
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
		User u = userService.getUserByUsername(username);
		userService.eliminarCuenta(username);
		emailService.sendMail(u.getEmail(), "Donapp - Cuenta eliminada", "Tu cuenta ha sido eliminada. Si no has sido tú probablemente te hayan baneado. Ponte en contacto con el soporte de Donapp para reclamaciones.");
		new SecurityContextLogoutHandler().logout(request, response, auth);
		return "redirect:/#";
	}

	@GetMapping("/eliminarUsuario/{id}")
	public String eliminarCuenta(Model model, @PathVariable Long id) {
		userService.eliminarCuentaPorId(id);
		return "redirect:/buscarUsuarios?usuarioEliminadoExito";
	}
	
	@GetMapping("/notificacionGeneral")
	public String notificacionGeneral() {
		return "notificacionGeneral";
	}
	
	@PostMapping("/enviarNotificacion")
    public String enviarNotificacion(@RequestParam("asunto") String asunto,
                                     @RequestParam("mensaje") String mensaje) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
        List<User> usuarios = userService.getAllUsersExceptActive(username);
        for (User usuario : usuarios) {
        	emailService.sendMail(usuario.getEmail(), asunto, mensaje);
        }
        return "redirect:/notificacionGeneral?mensajeEnviado"; 
    }

}
