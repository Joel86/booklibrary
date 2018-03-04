package be.joelv.services;

import java.util.List;
import java.util.Optional;

import be.joelv.entities.Publisher;

public interface PublisherService {
	void create(Publisher publisher);
	Optional<Publisher> read(long id);
	void update(Publisher publisher);
	void delete(Publisher publisher);
	List<Publisher> findAll();
	long countPublishers();
	Optional<Publisher> findByName(String name);
}
