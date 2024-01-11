package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iyan.donapp.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	@Query("SELECT p FROM Producto p WHERE(p.usuario.id LIKE ?1)")
	List<Producto> findAllByUserId(Long id);

	@Query("SELECT p FROM Producto p WHERE(p.usuario.id NOT LIKE ?1)")
	List<Producto> findAllExceptActiveUser(Long id);

	@Query("SELECT p FROM Producto p WHERE(p.interesado.id LIKE ?1)")
	List<Producto> findAllProductsObtainedByUser(Long id);

	@Query("SELECT p FROM Producto p WHERE p.interesado IS NOT NULL AND p.usuario.id = ?1")
	List<Producto> findAllProductsDonatedByUser(Long id);

	@Query("SELECT p FROM Producto p " +
	           "WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) " +
	           "AND (p.urgencia = :urgencia OR :urgencia = '') " +
	           "AND (p.tipo = :tipo OR :tipo = '') " +
	           "AND (p.formaEntrega = :formaEntrega OR :formaEntrega = '') " +
	           "AND p.usuario.id != :usuarioId")
	    List<Producto> buscarProductosConFiltros(
	            @Param("titulo") String titulo,
	            @Param("urgencia") String urgencia,
	            @Param("tipo") String tipo,
	            @Param("formaEntrega") String formaEntrega,
	            @Param("usuarioId") Long usuarioId);


}
