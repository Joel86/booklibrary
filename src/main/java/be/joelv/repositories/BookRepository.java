package be.joelv.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import be.joelv.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByIsbn10(String isbn);
	Page<Book> findByUsersUserId(@Param("id") long userId, Pageable pageable);
	Page<Book> findByYearAndUsersUserId(int year, long userId, Pageable pageable);
	Page<Book> findByTitleStartingWithAndUsersUserId(String title, long userId, Pageable pageable);
	List<Integer> findDistinctYearsByUser(@Param("id") long userId);
	List<String>findDistinctFirstCharByUser(@Param("id") long userId);
}
