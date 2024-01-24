package com.iyan.donapp.model.dto;

import java.util.Collection;

import com.iyan.donapp.model.Rol;

public class UserRegistroDto {

	private Long id;
	private String username;
	private String email;
	private String password;
	private Collection<Rol> roles;

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

	public UserRegistroDto(Long id, String username, String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public UserRegistroDto(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public UserRegistroDto(String username, String email, String password, Collection<Rol> roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.setRoles(roles);
	}

	public UserRegistroDto(String email) {
		super();
		this.email = email;
	}

	public UserRegistroDto() {

	}

	public Collection<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}

}
