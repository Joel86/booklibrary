package be.joelv.services;

import java.util.List;
import java.util.Optional;

import be.joelv.entities.Genre;

public interface GenreService {
	void create(Genre genre);
	Optional<Genre> read(long id);
	void update(Genre genre);
	void delete(Genre genre);
	List<Genre> findAll();
	long countGenres();
	Optional<Genre> findByName(String name);
}
