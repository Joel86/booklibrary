package be.joelv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.joelv.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	Genre findByName(String name);
}