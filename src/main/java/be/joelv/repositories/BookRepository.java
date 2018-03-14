package be.joelv.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import be.joelv.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByIsbn10(String isbn);
	Page<Book> findByUsers_Username(String username, Pageable pageable);
}
