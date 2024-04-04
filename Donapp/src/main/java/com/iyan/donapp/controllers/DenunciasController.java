package com.iyan.donapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.DenunciaDto;
import com.iyan.donapp.services.DenunciasService;
import com.iyan.donapp.services.UserService;
import com.iyan.donapp.services.validation.DenunciasValidation;

@Controller
public class DenunciasController {

	@Autowired
	private DenunciasService denunciasService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DenunciasValidation validador;

	@GetMapping("/denuncias")
	public String mostrarConversaciones(Model model) {
		model.addAttribute("denuncias", denunciasService.getAllDenuncias());
		return "admin/denuncias";
	}
	
	@PostMapping("/denunciar")
	public String denunciarUsuario(@ModelAttribute DenunciaDto denunciaDto, Model model) {
	    String contenidoDenuncia = denunciaDto.getContenidoDenuncia();
	    
	    if (!validador.validarMensaje(contenidoDenuncia)) {
	        return "redirect:/buscarUsuarios?errorMensaje";
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName();
	    User denunciante = userService.getUserByUsername(email);
	    User denunciado = userService.getUserById(denunciaDto.getUsuarioId());

	    if (denunciasService.denunciarUsuario(denunciante, denunciado, contenidoDenuncia)) {
	        return "redirect:/buscarUsuarios?usuarioDenunciado";
	    } else {
	        return "redirect:/buscarUsuarios?errorDenuncia";
	    }
	}

	
	@GetMapping("/cerrarDenuncia/{id}")
    public String cerrarDenuncia(@PathVariable Long id) {
        if (denunciasService.cerrarDenuncia(denunciasService.getById(id))) {
            return "redirect:/denuncias?denunciaCerrada";
        } else {
            return "redirect:/denuncias?errorCerrarDenuncia";
        }
    }
}

