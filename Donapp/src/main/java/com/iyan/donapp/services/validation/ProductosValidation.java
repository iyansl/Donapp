package com.iyan.donapp.services.validation;

import org.springframework.stereotype.Component;

import com.iyan.donapp.model.dto.ProductoDto;

@Component
public class ProductosValidation {

	public boolean validarDatosVaciosProducto(ProductoDto p) {
		if (p.getTitulo() == null || p.getTitulo().isEmpty()) {
			return false;
		} else if (p.getSubtitulo() == null || p.getSubtitulo().isEmpty()) {
			return false;
		} else if (p.getDescripcionEntrega() == null || p.getDescripcionEntrega().isEmpty()
				|| p.getDescripcionEntrega().length() < 4) {
			return false;
		} else if (p.getFoto().getOriginalFilename() == null || p.getFoto().getOriginalFilename().isEmpty()
				|| p.getFoto().getOriginalFilename().isBlank()) {
			return false;
		}
		return true;
	}

	public boolean validarDatosCortosProducto(ProductoDto p) {
		if (p.getTitulo().length() < 4) {
			return false;
		} else if (p.getSubtitulo().length() < 4) {
			return false;
		} else if (p.getDescripcionEntrega().length() < 4) {
			return false;
		}
		return true;
	}
}
