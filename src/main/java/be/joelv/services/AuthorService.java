package be.joelv.services;

import java.util.List;
import java.util.Optional;

import be.joelv.entities.Author;

public interface AuthorService {
	void create(Author author);
	Optional<Author> read(long id);
	void update(Author author);
	void delete(Author author);
	List<Author> findAll();
	long countAuthors();
	Optional<Author> findByNameAndSurname(String name, String surname);
}
