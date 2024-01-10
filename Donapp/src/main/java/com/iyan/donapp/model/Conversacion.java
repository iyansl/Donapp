package com.iyan.donapp.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "conversaciones")
public class Conversacion {
	
	public Conversacion(User usuario1, User usuario2) {
		this.usuario1 = usuario1;
		this.usuario2 = usuario2;
		this.setVisibilidadUsuario1(true);
		this.setVisibilidadUsuario2(true);
		this.setMensajes(new ArrayList<Mensaje>());
	}
	
	public Conversacion() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario1_id")
	private User usuario1;

	@ManyToOne
	@JoinColumn(name = "usuario2_id")
	private User usuario2;
	
	@Column(name = "visibilidad_usuario1")
	private boolean visibilidadUsuario1;
	
	@Column(name = "visibilidad_usuario2")
	private boolean visibilidadUsuario2;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "conversacion")
	private List<Mensaje> mensajes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(User usuario1) {
		this.usuario1 = usuario1;
	}

	public User getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(User usuario2) {
		this.usuario2 = usuario2;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public boolean isVisibilidadUsuario1() {
		return visibilidadUsuario1;
	}

	public void setVisibilidadUsuario1(boolean visibilidadUsuario1) {
		this.visibilidadUsuario1 = visibilidadUsuario1;
	}

	public boolean isVisibilidadUsuario2() {
		return visibilidadUsuario2;
	}

	public void setVisibilidadUsuario2(boolean visibilidadUsuario2) {
		this.visibilidadUsuario2 = visibilidadUsuario2;
	}
	
	
	
}
