package be.joelv.services;

import java.util.List;
import java.util.Optional;

import be.joelv.entities.User;

public interface UserService {
	void create(User user);
	Optional<User> read(long id);
	void update(User user);
	void delete(User user);
	List<User> findAll();
	long countUsers();
	Optional<User> read(String username);
}
