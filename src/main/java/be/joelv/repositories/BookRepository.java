package be.joelv.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import be.joelv.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByIsbn10(String isbn);
	Page<Book> findByUsers_Username(String username, Pageable pageable);
	Page<Book> findByYearAndUsers_Username(int year, String username, Pageable pageable);
	List<Integer> findDistinctYearsByUser(@Param("username") String username);
}
