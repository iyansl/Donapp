package com.iyan.donapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Mensaje;
import com.iyan.donapp.repositories.MensajesRepository;

@Service
public class MensajesService {
	
	@Autowired
	private MensajesRepository mr;

	public void save(Mensaje mensaje) {
		mr.save(mensaje);
	}

}
