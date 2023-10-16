package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	@Query("SELECT p FROM Producto p WHERE(p.usuario.id LIKE ?1)")
	List<Producto> findAllByUserId(Long id);

	@Query("SELECT p FROM Producto p WHERE(p.usuario.id NOT LIKE ?1)")
	List<Producto> findAllExceptActiveUser(Long id);
}
