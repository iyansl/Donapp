package com.iyan.donapp.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductoDto {

	private Long id;
	private String titulo;
	private String subtitulo;
	private String urgencia;
	private String tipo;
	private String estado;
	private String formaEntrega;
	private String descripcionEntrega;
	private MultipartFile foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductoDto(MultipartFile foto, String titulo, String subtitulo, String urgencia, String tipo, String formaEntrega, String descripcionEntrega, String estado) {
		this.setFoto(foto);
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.urgencia = urgencia;
		this.tipo = tipo;
		this.formaEntrega = formaEntrega;
		this.descripcionEntrega = descripcionEntrega;
		this.estado = estado;
	}
	
	public ProductoDto(String titulo, String subtitulo, String urgencia, String tipo, String formaEntrega, String descripcionEntrega, String estado) {
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.tipo = tipo;
		this.urgencia = urgencia;
		this.formaEntrega = formaEntrega;
		this.descripcionEntrega = descripcionEntrega;
		this.estado = estado;
	}
	
	public ProductoDto() {

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

	public MultipartFile getFoto() {
		return foto;
	}

	public void setFoto(MultipartFile foto) {
		this.foto = foto;
	}
	
	

}
