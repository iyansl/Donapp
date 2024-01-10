package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.Conversacion;

public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {

	@Query("SELECT c FROM Conversacion c WHERE(c.usuario1.id LIKE ?1)")
	List<Conversacion> findConversacionesByUser1Id(Long id);

	@Query("SELECT c FROM Conversacion c WHERE(c.usuario2.id LIKE ?1)")
	List<Conversacion> findConversacionesByUser2Id(Long id);

	@Query("SELECT c FROM Conversacion c WHERE((c.usuario1.id LIKE ?1 and c.usuario2.id LIKE ?2) or (c.usuario2.id LIKE ?1 and c.usuario1.id LIKE ?2))")
	Conversacion getConversacionByUser1AndUser2(Long id1, Long id2);

	@Query("SELECT c FROM Conversacion c WHERE(c.usuario1.id LIKE ?1 or c.usuario2.id LIKE ?1)")
	Conversacion getConversacionByUserId(Long long1);

	@Modifying
	@Query("UPDATE Conversacion c SET c.visibilidadUsuario1 = true, c.visibilidadUsuario2 = true WHERE c = ?1")
	void setVisible(Conversacion c1);

}
