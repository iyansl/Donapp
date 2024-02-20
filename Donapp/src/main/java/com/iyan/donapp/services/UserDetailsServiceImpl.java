package com.iyan.donapp.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iyan.donapp.model.Rol;
import com.iyan.donapp.model.User;
import com.iyan.donapp.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null || !user.isActivado())
			throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
		Collection<? extends GrantedAuthority> grantedAuthorities = this.mapRoles(user.getRoles());
		UserDetailsImpl u = new UserDetailsImpl(user.getEmail(), user.getPassword(), grantedAuthorities);
		u.setUsername(user.getUsername());
		return u;
	}

	private Collection<? extends GrantedAuthority> mapRoles(Collection<Rol> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

}
