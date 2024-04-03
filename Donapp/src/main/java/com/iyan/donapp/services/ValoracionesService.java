package com.iyan.donapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.User;
import com.iyan.donapp.model.Valoracion;
import com.iyan.donapp.repositories.ValoracionesRepository;

@Service
public class ValoracionesService {

	@Autowired
	private ValoracionesRepository vr;

	public List<Valoracion> getAllValoraciones() {
		return vr.findAll();
	}

	public boolean valorarUsuario(User valorador, User valorado, String contenido, int puntuacion) {
		Valoracion v = new Valoracion(valorador, valorado, contenido, puntuacion);
		if (vr.save(v) != null)
			return true;
		return false;
	}

	public Valoracion getById(Long id) {
		return vr.findById(id).get();
	}

	public List<Valoracion> findAllByUserId(Long id) {
		return vr.findByUserId(id);
	}

	public boolean usuarioYaValorado(User valorador, User valorado) {
		List<Valoracion> valoracionesUser = vr.findByUserId(valorado.getId());
		for (Valoracion valoracion : valoracionesUser) {
			if (valoracion.getUsuarioValorador().getId().equals(valorador.getId())) {
				return true;
			}
		}
		return false;
	}

}
