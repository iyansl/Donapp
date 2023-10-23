package com.iyan.donapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE(LOWER(u.username) LIKE LOWER(?1))")
	public User findByUsername(String username);
}
