package com.iyan.donapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/", "/recuperarcontrasena","/recuperarcontrasena/*", "/iniciarsesion", "/registrarse", "/registrarse/*", "/ayuda", "/conocenos", "/estilos/**", "/img/**").permitAll()
				.requestMatchers("/adminUsers", "/denuncias", "/cerrarDenuncia/*", "eliminarUsuario/*").hasRole("ADMIN")
				.anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/iniciarsesion").defaultSuccessUrl("/").permitAll())
				.logout((logout) -> logout.logoutSuccessUrl("/iniciarsesion?logout").invalidateHttpSession(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")));

		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}
	
	

}