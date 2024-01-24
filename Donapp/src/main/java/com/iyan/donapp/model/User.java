package com.iyan.donapp.model;

import java.util.Base64;
import java.util.Collection;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "email")
	private String email;

	@Lob
	@Column(name = "foto", columnDefinition = "MEDIUMBLOB")
	private byte[] foto;

	private String password;

	@Transient
	private String fotoEncoded;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Collection<Rol> roles;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "usuario")
	private Set<Producto> productos;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "usuario")
	private Set<Producto> productosObtenidos;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "receptor")
	private Set<Solicitud> solicitudesRecibidas;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "solicitante")
	private Set<Solicitud> solicitudesEnviadas;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Mensaje> mensajesEnviados;
	
	@OneToMany(mappedBy = "usuario1", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Conversacion> conversaciones1;
	
	@OneToMany(mappedBy = "usuario2", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Conversacion> conversaciones2;

	public User(Long id, String username, String descripcion, String email, String password, Collection<Rol> roles) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.descripcion = descripcion;
	}

	public User(String username, String email, String password, Collection<Rol> roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFoto(byte[] bytes) {
		this.foto = bytes;
	}

	public byte[] getFoto() {
		return foto;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public Set<Solicitud> getSolicitudesRecibidas() {
		return solicitudesRecibidas;
	}

	public void setSolicitudesRecibidas(Set<Solicitud> solicitudesRecibidas) {
		this.solicitudesRecibidas = solicitudesRecibidas;
	}

	public Set<Solicitud> getSolicitudesEnviadas() {
		return solicitudesEnviadas;
	}

	public void setSolicitudesEnviadas(Set<Solicitud> solicitudesEnviadas) {
		this.solicitudesEnviadas = solicitudesEnviadas;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public String getFotoEncoded() {
		return fotoEncoded;
	}

	public void setFotoEncoded(String fotoEncoded) {
		this.fotoEncoded = fotoEncoded;
	}

	public void updateFotoEncoded() {
		this.fotoEncoded = Base64.getEncoder().encodeToString(this.getFoto());
		this.getProductos().forEach(p -> p.updateFotoEncoded());
		this.getProductosObtenidos().forEach(p -> p.updateFotoEncoded());
	}

	public Set<Producto> getProductosObtenidos() {
		return productosObtenidos;
	}

	public void setProductosObtenidos(Set<Producto> productosObtenidos) {
		this.productosObtenidos = productosObtenidos;
	}
	
	public Set<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}

	public void setMensajesEnviados(Set<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

	public Set<Conversacion> getConversaciones1() {
		return conversaciones1;
	}

	public void setConversaciones1(Set<Conversacion> conversaciones1) {
		this.conversaciones1 = conversaciones1;
	}

	public Set<Conversacion> getConversaciones2() {
		return conversaciones2;
	}

	public void setConversaciones2(Set<Conversacion> conversaciones2) {
		this.conversaciones2 = conversaciones2;
	}

	

}
