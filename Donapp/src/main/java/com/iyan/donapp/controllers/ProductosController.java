package com.iyan.donapp.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.ProductoDto;
import com.iyan.donapp.services.ProductoService;
import com.iyan.donapp.services.UserService;
import com.iyan.donapp.services.provinces.ProvincesApi;
import com.iyan.donapp.services.validation.ProductosValidation;

@Controller
public class ProductosController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ProductosValidation validador;
	
	@Autowired
	private ProvincesApi provincesApi;

	@GetMapping("/mercado")
	public String mercado(@RequestParam(name = "busqueda", required = false, defaultValue = "") String busqueda,
			@RequestParam(name = "provincia", required = false, defaultValue = "Todos") String provincia,
			@RequestParam(name = "urgencia", required = false, defaultValue = "Todos") String urgencia,
			@RequestParam(name = "tipo", required = false, defaultValue = "Todos") String tipo,
			@RequestParam(name = "recogida", required = false, defaultValue = "Todos") String recogida, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		List<Producto> productos;
		if (busqueda.isEmpty() && "Todas".equals(urgencia) && "Todos".equals(tipo) && "Todos".equals(recogida)) {
			productos = productoService.getAllProductosExceptActiveUser(obtained.getId());
		} else {
			productos = productoService.buscarProductosConFiltros(busqueda, provincia, urgencia, tipo, recogida, obtained.getId());
		}
		List<String> provincias = new ArrayList<String>();
		provincias.add("Todos");
		provincias.addAll(provincesApi.getAll());
        model.addAttribute("provincias", provincias);
		model.addAttribute("productos", productos);
		return "productos/mercado";
	}

	@GetMapping("/publicar")
	public String publicar(Model model) {
        model.addAttribute("provincias", provincesApi.getAll());
        return "productos/publicar";
	}

	@PostMapping("/publicar")
	public String publicar(@ModelAttribute("producto") ProductoDto dto) {
		if (!validador.validarDatosVaciosProducto(dto)) {
			return "redirect:/publicar?faltanDatos";
		} else if (!validador.validarDatosCortosProducto(dto)) {
			return "redirect:/publicar?faltanDatosLargos";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		productoService.saveProducto(dto, obtained);
		return "redirect:/publicados?exito";
	}

	@GetMapping("/publicados")
	public String publicados(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("productos", productoService.findAllByUserId(obtained.getId()));
		return "productos/publicados";
	}

	@GetMapping("/adquiridos")
	public String adquiridos(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("adquiridos", productoService.findAllObtainedByUserId(obtained.getId()));
		return "productos/adquiridos";
	}

	@GetMapping("/donados")
	public String donados(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		model.addAttribute("donados", productoService.findAllDonatedByUserId(obtained.getId()));
		return "productos/donados";
	}

	@RequestMapping("/producto/{id}")
	public String producto(@PathVariable Long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		Producto producto = productoService.getProductoById(id);
		model.addAttribute("producto", producto);
		model.addAttribute("provincias", provincesApi.getAll());
		if (producto.getUsuario() == obtained) {
			model.addAttribute("editar", true);
		} else if (producto.getInteresado() == obtained) {
			model.addAttribute("editar", false);
			model.addAttribute("solicitar", false);
		} else {
			model.addAttribute("solicitar", true);
		}
		return "productos/producto";
	}

	@PostMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id, @ModelAttribute("producto") ProductoDto dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		if (productoService.getProductoById(dto.getId()).getUsuario().getId() != obtained.getId())
	        return "redirect:/producto/" + id + "?errorProducto";
		if (productoService.updateProducto(dto, id, obtained) == null)
			return "redirect:/producto/" + id + "?errorUser";
		return "redirect:/producto/" + id + "?exito";
	}

	@GetMapping("/eliminarProducto/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		boolean esAdmin = obtained.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equals("ROLE_ADMIN"));
		if (productoService.getProductoById(id).getUsuario().getId() != obtained.getId() && !esAdmin)
	        return "redirect:/producto/" + id + "?errorProducto";
		productoService.deleteProductoById(id);
		return "redirect:/mercado?exitoEliminandoProducto";
	}

	@PostMapping("/subirFotoProducto/{id}")
	public String subirFoto(@PathVariable Long id, @RequestParam("foto") MultipartFile foto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User obtained = userService.getUserByUsername(email);
		if (productoService.getProductoById(id).getUsuario().getId() != obtained.getId())
	        return "redirect:/producto/" + id + "?errorProducto";
		if (foto.getSize() != 0) {
			if (foto.getSize() > 3000000)
				return "redirect:/producto/" + id + "?error";
			else {
				if (productoService.cambiarFoto(foto, id, obtained) == null)
					return "redirect:/producto/" + id + "?errorUser";
			}
		}
		return "redirect:/producto/" + id + "?exito";
	}

}
