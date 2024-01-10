package com.iyan.donapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Conversacion;
import com.iyan.donapp.model.Mensaje;
import com.iyan.donapp.model.User;
import com.iyan.donapp.repositories.ConversacionRepository;
import com.iyan.donapp.repositories.MensajesRepository;

import jakarta.transaction.Transactional;

@Service
public class ConversacionesService {

	@Autowired
	private ConversacionRepository conversacionRepository;
	
	@Autowired
	private MensajesRepository mensajesRepository;

	public List<Conversacion> getConversacionesBy1Id(Long id) {
		List<Conversacion> lista = conversacionRepository.findConversacionesByUser1Id(id);
		List<Conversacion> listaFiltrada = lista.stream().filter(conversacion -> conversacion.isVisibilidadUsuario1())
				.collect(Collectors.toList());
		return listaFiltrada;
	}

	public List<Conversacion> getConversacionesBy2Id(Long id) {
		List<Conversacion> lista = conversacionRepository.findConversacionesByUser2Id(id);
		List<Conversacion> listaFiltrada = lista.stream().filter(conversacion -> conversacion.isVisibilidadUsuario2())
				.collect(Collectors.toList());
		return listaFiltrada;
	}

	@Transactional
	public void save(Conversacion c1) {
		if (conversacionRepository.getConversacionByUser1AndUser2(c1.getUsuario1().getId(),
				c1.getUsuario2().getId()) == null) {
			conversacionRepository.save(c1);
		} else {
			Conversacion existingConversacion = conversacionRepository
					.getConversacionByUser1AndUser2(c1.getUsuario1().getId(), c1.getUsuario2().getId());
			existingConversacion.setVisibilidadUsuario1(true);
			existingConversacion.setVisibilidadUsuario2(true);
			conversacionRepository.save(existingConversacion);
		}
	}

	public void removeUserFromConversation(User user, Conversacion c1) {
		if (getConversacionById(c1.getId()) != null) {
			if (c1.getUsuario1().getId() == user.getId())
				c1.setVisibilidadUsuario1(false);
			else if (c1.getUsuario2().getId() == user.getId())
				c1.setVisibilidadUsuario2(false);
			conversacionRepository.save(c1);
		}
	}

	public Conversacion getConversacionById(Long id) {
		return conversacionRepository.findById(id).get();
	}

    public void actualizarMensajes(Conversacion conversacion) {
        List<Mensaje> mensajes = mensajesRepository.findMensajesByConversacionIdOrderByTimestamp(conversacion.getId());
        conversacion.setMensajes(mensajes);
        conversacionRepository.save(conversacion);
    }

}
