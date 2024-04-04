package com.iyan.donapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.iyan.donapp.model.Conversacion;
import com.iyan.donapp.model.Mensaje;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.MensajeDto;
import com.iyan.donapp.services.ConversacionesService;
import com.iyan.donapp.services.MensajesService;
import com.iyan.donapp.services.UserService;

@Controller
public class ChatController {

	@Autowired
	private ConversacionesService conversacionesService;
	
	@Autowired
	private MensajesService mensajesService;

	@Autowired
	private UserService userService;

	@GetMapping("/conversaciones")
	public String mostrarConversaciones(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		List<Conversacion> conversacionesIniciadas = conversacionesService.getConversacionesBy1Id(obtained.getId());
		model.addAttribute("conversacionesIniciadas", conversacionesIniciadas);
		List<Conversacion> conversacionesSeguidas = conversacionesService.getConversacionesBy2Id(obtained.getId());
		model.addAttribute("conversacionesSeguidas", conversacionesSeguidas);
		return "usuarios/conversaciones";
	}

	@GetMapping("/hablar/{id}")
	public String hablar(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		User user = userService.getUserById(id);
		Conversacion c = new Conversacion(obtained, user);
		conversacionesService.save(c);
		return "redirect:/conversaciones";
	}
	
	@GetMapping("/borrar/{id}")
	public String borrar(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		conversacionesService.removeUserFromConversation(obtained, conversacionesService.getConversacionById(id));
		return "redirect:/conversaciones";
	}

	@GetMapping("/chat/{id}")
	public String mostrarMensajes(@PathVariable Long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Conversacion c = conversacionesService.getConversacionById(id);
		if (c == null)
			return "redirect:/conversaciones";
		model.addAttribute("mensajes", c.getMensajes());
		model.addAttribute("activeUser", obtained.getId());
		model.addAttribute("conversacion", c);
		return "usuarios/chat";
	}

	@PostMapping("/enviarMensaje")
	public String enviarMensaje(@ModelAttribute MensajeDto mensajeDto) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName();
	    User remitente = userService.getUserByUsername(email);
	    Conversacion conversacion = conversacionesService.getConversacionById(mensajeDto.getConversacionId());
	    Mensaje mensaje = new Mensaje(conversacion, remitente, mensajeDto.getContenido());
	    mensajesService.save(mensaje);
	    conversacion.getMensajes().add(mensaje);
	    return "redirect:/chat/" + mensajeDto.getConversacionId();
	}


}
