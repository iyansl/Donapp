package com.iyan.donapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iyan.donapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE(LOWER(u.username) LIKE LOWER(?1))")
	public User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE(LOWER(u.username) NOT LIKE LOWER(?1))")
	public List<User> findAllExceptActive(String username);

	@Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(concat('%', ?1, '%')) AND u.id <> ?2")
	public List<User> findByUsernameContainingIgnoreCaseAndIdNot(String username, Long id);
}
