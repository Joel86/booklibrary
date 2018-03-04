package be.joelv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.joelv.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}