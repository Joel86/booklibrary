package be.joelv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.joelv.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	Author findByNameAndSurname(String name, String surname);
}
