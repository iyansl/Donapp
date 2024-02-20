package com.iyan.donapp.repositories;

import java.time.LocalDateTime;
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

	@Query("SELECT u FROM User u WHERE(LOWER(u.token) LIKE LOWER(?1))")
	public User findByToken(String token);

	@Query("SELECT u FROM User u WHERE u.createdDate < :dateTime AND u.activado = false")
	public List<User> findByCreatedDateBeforeAndVerifiedFalse(LocalDateTime dateTime);
}
