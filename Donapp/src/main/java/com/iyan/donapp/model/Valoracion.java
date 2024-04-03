package com.iyan.donapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "valoraciones")
public class Valoracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario_valorador_id", nullable = false)
	private User usuarioValorador;
	
	@ManyToOne
	@JoinColumn(name = "usuario_valorado_id", nullable = false)
	private User usuarioValorado;
	
	@Column(name = "textoValoracion")
	private String textoValoracion;
	
	@Column(name = "puntuacion")
	private int puntuacion;

	public Valoracion() {
	}

	public Valoracion(User usuarioValorador, User usuarioValorado, String textoValoracion, int puntuacion) {
		this.usuarioValorador = usuarioValorador;
		this.usuarioValorado = usuarioValorado;
		this.textoValoracion = textoValoracion;
		this.puntuacion = puntuacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUsuarioValorador() {
		return usuarioValorador;
	}

	public void setUsuarioValorador(User usuarioValorador) {
		this.usuarioValorador = usuarioValorador;
	}

	public User getUsuarioValorado() {
		return usuarioValorado;
	}

	public void setUsuarioValorado(User usuarioValorado) {
		this.usuarioValorado = usuarioValorado;
	}

	public String getTextoValoracion() {
		return textoValoracion;
	}

	public void setTextoValoracion(String textoValoracion) {
		this.textoValoracion = textoValoracion;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	
	
}
