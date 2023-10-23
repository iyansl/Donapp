package com.iyan.donapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.SolicitudDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.SolicitudService;
import com.iyan.donapp.services.UserService;

@Controller
public class SolicitudesController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@RequestMapping("/solicitar/{id}")
	public String producto(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Producto producto = productoService.getProductoById(id); 
        SolicitudDto dto = new SolicitudDto(obtained, producto.getUsuario(), producto);
        solicitudService.saveSolicitud(dto);
		return "redirect:/solicitudes?exitoSolicitud";
	}
	
	@GetMapping("/solicitudes")
	public String recibidas(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("enviadas", solicitudService.getSolicitudesEnviadasByUserId(obtained.getId()));
		model.addAttribute("recibidas", solicitudService.getSolicitudesRecibidasByUserId(obtained.getId()));
		return "solicitudes";
	}

}
