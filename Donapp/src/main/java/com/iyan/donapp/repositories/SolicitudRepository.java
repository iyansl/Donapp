package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long>{
	@Query("SELECT s FROM Solicitud s WHERE(s.solicitante.id LIKE ?1)")
	List<Solicitud> getSolicitudesEnviadasByUserId(Long id);
	
	@Query("SELECT s FROM Solicitud s WHERE(s.receptor.id LIKE ?1)")
	List<Solicitud> getSolicitudesRecibidasByUserId(Long id);
	
}
