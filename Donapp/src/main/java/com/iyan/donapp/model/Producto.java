package com.iyan.donapp.model;

import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "productos", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User usuario;
	
	@ManyToOne
    @JoinColumn(name = "interesado_id")
    private User interesado;

	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "subtitulo")
	private String subtitulo;
	
	@Column(name = "urgencia")
	private String urgencia;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "formaEntrega")
	private String formaEntrega;
	
	@Column(name = "descripcionEntrega")
	private String descripcionEntrega;
	
	@Lob
	@Column(name = "foto", columnDefinition = "MEDIUMBLOB")
    private byte[] foto;
	
	@Transient
	private String fotoEncoded;
	
	public Producto(byte[] foto, String titulo, String subtitulo, String urgencia, String tipo, String formaEntrega, String descripcionEntrega, String estado) {
		super();
		this.foto = foto;
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.urgencia = urgencia;
		this.tipo = tipo;
		this.estado = estado;
		this.formaEntrega = formaEntrega;
		this.descripcionEntrega = descripcionEntrega;
	}
	
	public Producto(String titulo, String subtitulo, String tipo) {
		super();
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.tipo = tipo;
	}
	

	public Producto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getFormaEntrega() {
		return formaEntrega;
	}


	public void setFormaEntrega(String formaEntrega) {
		this.formaEntrega = formaEntrega;
	}


	public String getDescripcionEntrega() {
		return descripcionEntrega;
	}


	public void setDescripcionEntrega(String descripcionEntrega) {
		this.descripcionEntrega = descripcionEntrega;
	}


	public User getUsuario() {
		return usuario;
	}


	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}


	public void setFotoEncoded(String encoded) {
		this.fotoEncoded = encoded;
	}	
	
	public String getFotoEncoded() {
		return fotoEncoded;
	}
	
	public void updateFotoEncoded() {
		this.fotoEncoded = Base64.getEncoder().encodeToString(this.getFoto());
	}

	public User getInteresado() {
		return interesado;
	}

	public void setInteresado(User interesado) {
		this.interesado = interesado;
	}
	
	
}
