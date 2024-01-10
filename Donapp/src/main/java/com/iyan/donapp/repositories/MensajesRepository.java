package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.Mensaje;

public interface MensajesRepository extends JpaRepository<Mensaje, Long> {

	@Query("SELECT m FROM Mensaje m WHERE m.conversacion.id = ?1 ORDER BY m.timestamp")
	List<Mensaje> findMensajesByConversacionIdOrderByTimestamp(Long conversacionId);


}
