package com.iyan.donapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "denuncias")
public class Denuncia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario_denunciante_id", nullable = false)
	private User usuarioDenunciante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_denunciado_id", nullable = false)
	private User usuarioDenunciado;
	
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	@Column(name = "contenido")
	private String contenido;

	public Denuncia() {
	}

	public Denuncia(User usuarioDenunciante, User usuarioDenunciado, String contenido) {
		this.contenido = contenido;
		this.usuarioDenunciante = usuarioDenunciante;
		this.usuarioDenunciado = usuarioDenunciado;
		this.timestamp = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUsuarioDenunciante() {
		return usuarioDenunciante;
	}

	public void setUsuarioDenunciante(User usuarioDenunciante) {
		this.usuarioDenunciante = usuarioDenunciante;
	}

	public User getUsuarioDenunciado() {
		return usuarioDenunciado;
	}

	public void setUsuarioDenunciado(User usuarioDenunciado) {
		this.usuarioDenunciado = usuarioDenunciado;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	
}
