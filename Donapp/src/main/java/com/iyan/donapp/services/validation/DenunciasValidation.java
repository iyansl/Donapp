package com.iyan.donapp.services.validation;

import org.springframework.stereotype.Component;

@Component
public class DenunciasValidation {

	public boolean validarMensaje(String mensaje) {
		if (mensaje == null || mensaje.isEmpty() || mensaje.length() < 4) {
			return false;
		} 
		return true;
	}
}
