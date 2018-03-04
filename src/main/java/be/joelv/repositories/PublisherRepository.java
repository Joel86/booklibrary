package be.joelv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.joelv.entities.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	Publisher findByName(String name);
}
