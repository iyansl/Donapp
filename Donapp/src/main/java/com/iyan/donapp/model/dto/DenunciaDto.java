package com.iyan.donapp.model.dto;

public class DenunciaDto {
	
	private Long usuarioId;
	private String contenidoDenuncia;

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getContenidoDenuncia() {
		return contenidoDenuncia;
	}

	public void setContenidoDenuncia(String contenidoDenuncia) {
		this.contenidoDenuncia = contenidoDenuncia;
	}
}
