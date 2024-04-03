package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.Valoracion;

public interface ValoracionesRepository extends JpaRepository<Valoracion, Long> {

	@Query("SELECT v FROM Valoracion v WHERE v.usuarioValorado.id = ?1")
	List<Valoracion> findByUserId(Long id);

}
