package com.iyan.donapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.Solicitud;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.SolicitudDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.SolicitudService;
import com.iyan.donapp.services.UserService;
import com.iyan.donapp.services.email.EmailSender;

@Controller
public class SolicitudesController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private EmailSender emailService;

	@RequestMapping("/solicitar/{id}")
	public String producto(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Producto producto = productoService.getProductoById(id);
		List<Solicitud> lista = solicitudService.getSolicitudesEnviadasByUserId(obtained.getId());
		List<Producto> listaPublicados = productoService.findAllByUserId(obtained.getId());
		for (Solicitud s : lista) {
			if (s.getProducto().getId() == producto.getId())
				return "redirect:/mercado?solicitudYaEnviada";
		}
		for (Producto p : listaPublicados) {
			if (p.getId() == producto.getId())
				return "redirect:/mercado?productoEsPublicado";
		}
		SolicitudDto dto = new SolicitudDto(obtained, producto.getUsuario(), producto);
		solicitudService.saveSolicitud(dto);
		emailService.sendMail(producto.getUsuario().getEmail(), "Donapp - Solicitud recibida",
				"Acabas de recibir una solicitud de producto: " + producto.getTitulo() + ", ¡no te la pierdas!");

		return "redirect:/solicitudes?exitoSolicitud";
	}

	@RequestMapping("/aceptarSolicitud/{id}")
	public String aceptarSolicitud(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Solicitud solicitud = solicitudService.getSolicitudById(id);
		if (solicitud.getReceptor().getId() != obtained.getId() || !"Pendiente".equals(solicitud.getEstado()))
			return "redirect:/solicitudes?solicitudErronea";
		solicitudService.aceptarSolicitud(solicitud, solicitud.getSolicitante());
		emailService.sendMail(solicitud.getSolicitante().getEmail(), "Donapp - Solicitud aceptada",
				"¡Han aceptado tu solicitud de un producto (" + solicitud.getProducto().getTitulo()
						+ ")! Ponte en contacto con el usuario que lo haya publicado para organizar la entrega o recogida.");

		return "redirect:/solicitudes?exitoAceptarSolicitud";
	}

	@RequestMapping("/cancelarSolicitud/{id}")
	public String cancelarSolicitud(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Solicitud solicitud = solicitudService.getSolicitudById(id);
		if (solicitud.getReceptor().getId() != obtained.getId() || !"Pendiente".equals(solicitud.getEstado()))
			return "redirect:/solicitudes?solicitudErronea";
		solicitudService.cancelarSolicitud(solicitud, solicitud.getSolicitante());
		emailService.sendMail(solicitud.getSolicitante().getEmail(), "Donapp - Solicitud cancelada",
				"Lo siento, han cancelado tu solicitud de un producto (" + solicitud.getProducto().getTitulo()
						+ "). El usuario ha decidido no donártelo.");

		return "redirect:/solicitudes?exitoDenegarSolicitud";
	}

	@GetMapping("/solicitudes")
	public String recibidas(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("enviadas", solicitudService.getSolicitudesEnviadasByUserId(obtained.getId()));
		model.addAttribute("recibidas", solicitudService.getSolicitudesRecibidasByUserId(obtained.getId()));
		return "usuarios/solicitudes";
	}

}
