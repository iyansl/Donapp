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
@Table(name = "mensajes")
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "conversacion_id")
	private Conversacion conversacion;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private User usuario;

	@Column(name = "contenido")
	private String contenido;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public Mensaje() {
	}

	public Mensaje(Conversacion conversacion, User remitente, String contenido) {
		this.contenido = contenido;
		this.conversacion = conversacion;
		this.usuario = remitente;
		this.timestamp = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conversacion getConversacion() {
		return conversacion;
	}

	public void setConversacion(Conversacion conversacion) {
		this.conversacion = conversacion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
}
