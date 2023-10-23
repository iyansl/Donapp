package com.iyan.donapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitud")
public class Solicitud {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "solicitante_id")
	private User solicitante;
	
	@ManyToOne
    @JoinColumn(name = "receptor_id")
	private User receptor;
	
	private String estado;
	
	@ManyToOne
    @JoinColumn(name = "producto_id")
	private Producto producto;
	
	public Solicitud() {
		
	}
	
    public Solicitud(User solicitante, User receptor, Producto producto) {
		this.solicitante = solicitante;
		this.receptor = receptor;
		this.producto = producto;
		this.estado = "Pendiente";
	}
	
	public User getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(User solicitante) {
		this.solicitante = solicitante;
	}
	public User getReceptor() {
		return receptor;
	}
	public void setRropietario(User receptor) {
		this.receptor = receptor;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	
	public void updateFotosEncoded() {
		this.solicitante.updateFotoEncoded();
		this.receptor.updateFotoEncoded();
		this.producto.updateFotoEncoded();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setReceptor(User receptor) {
		this.receptor = receptor;
	}

}
