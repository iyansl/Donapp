package com.iyan.donapp.model.dto;

import com.iyan.donapp.model.Producto;
import com.iyan.donapp.model.User;

public class SolicitudDto {

	private User solicitante;
	private User solicitado;
	private Producto producto;
	
	public SolicitudDto(User solicitante, User solicitado, Producto producto) {
		this.solicitado = solicitado;
		this.solicitante = solicitante;
		this.producto = producto;
	}

	public User getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(User solicitante) {
		this.solicitante = solicitante;
	}

	public User getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(User solicitado) {
		this.solicitado = solicitado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	

}
