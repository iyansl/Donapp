package com.iyan.donapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Solicitud;
import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.SolicitudDto;
import com.iyan.donapp.repositories.SolicitudRepository;

@Service
public class SolicitudService {

	private SolicitudRepository solicitudRepository;

	public SolicitudService(SolicitudRepository solicitudRepository) {
		super();
		this.solicitudRepository = solicitudRepository;
	}
	
	public Solicitud saveSolicitud(SolicitudDto dto) {
		Solicitud sol = new Solicitud(dto.getSolicitante(), dto.getSolicitado(), dto.getProducto());
		return solicitudRepository.save(sol);
	}

	public List<Solicitud> getSolicitudesEnviadasByUserId(Long id) {
		List<Solicitud> lista = solicitudRepository.getSolicitudesEnviadasByUserId(id);
		for (Solicitud s: lista) {
			s.updateFotosEncoded();
		}
		return lista;
	}

	public List<Solicitud> getSolicitudesRecibidasByUserId(Long id) {
		List<Solicitud> lista = solicitudRepository.getSolicitudesRecibidasByUserId(id);
		for (Solicitud s: lista) {
			s.updateFotosEncoded();
		}
		return lista;
	}

	public Solicitud getSolicitudById(Long id) {
		return solicitudRepository.findById(id).get();
	}

	public void updateEstadoSolicitud(Solicitud solicitud, String estado) {
		solicitud.setEstado(estado);
		solicitudRepository.save(solicitud);
	}

	public void aceptarSolicitud(Solicitud solicitud, User user) {
		solicitud.setEstado("Aceptada");
		solicitud.getProducto().setInteresado(user);
		solicitudRepository.save(solicitud);
	}

}
