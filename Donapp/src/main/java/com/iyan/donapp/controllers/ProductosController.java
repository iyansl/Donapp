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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;

@Controller
public class ProductosController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductoService productoService;

	@GetMapping("/mercado")
	public String mercado(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("productos", productoService.getAllProductosExceptActiveUser(obtained.getId()));
		return "mercado";
	}
	
	@GetMapping("/publicar")
	public String publicar() {
		return "publicar";
	}
	
	@PostMapping("/publicar")
	public String publicar(@ModelAttribute("producto") ProductoDto dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		productoService.saveProducto(dto, obtained);
		return "redirect:/publicar?exito";
	}
	
	@GetMapping("/publicados")
	public String publicados(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("productos", productoService.findAllByUserId(obtained.getId()));
		return "publicados";
	}
	
	@GetMapping("/adquiridos")
	public String adquiridos() {
		return "adquiridos";
	}
	
	@RequestMapping("/producto/{id}")
	public String producto(@PathVariable Long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Producto producto = productoService.getProductoById(id); 
        model.addAttribute("producto", producto);
        if (producto.getUsuario() == obtained)
        	model.addAttribute("editar", true);
        else
        	model.addAttribute("solicitar", true);
		return "producto";
	}
	
	@PostMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id, @ModelAttribute("producto") ProductoDto dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		if (productoService.updateProducto(dto, id, obtained) == null)
			return "redirect:/producto/"+id+"?errorUser";	
		return "redirect:/producto/"+id+"?exito";
	}
	
	@PostMapping("/subirFotoProducto/{id}")
	public String subirFoto(@PathVariable Long id, @RequestParam("foto") MultipartFile foto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		if (foto.getSize() != 0) {
			if (foto.getSize() > 3000000)
				return "redirect:/producto/"+id+"?error";
			else {
				if (productoService.cambiarFoto(foto, id, obtained) == null)
					return "redirect:/producto/"+id+"?errorUser";	
			}
		}
		return "redirect:/producto/"+id+"?exito";
	}
	
}
