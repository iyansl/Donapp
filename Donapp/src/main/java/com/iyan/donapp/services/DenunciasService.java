package com.iyan.donapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Denuncia;
import com.iyan.donapp.model.User;
import com.iyan.donapp.repositories.DenunciasRepository;

@Service
public class DenunciasService {

	@Autowired
	private DenunciasRepository dr;

	public List<Denuncia> getAllDenuncias() {
		return dr.findAll();
	}

	public boolean denunciarUsuario(User denunciante, User denunciado, String contenido) {
		Denuncia d = new Denuncia(denunciante, denunciado, contenido);
		if (dr.save(d) != null)
			return true;
		return false;
	}

	public boolean cerrarDenuncia(Denuncia denuncia) {
		dr.delete(denuncia);
		return true;
	}

	public Denuncia getById(Long id) {
		return dr.findById(id).get();
	}
}
