package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.joelv.entities.Book;


public interface BookService {
	public void create(Book book);
	Optional<Book> read(long id);
	void update(Book book);
	void delete(Book book);
	List<Book> findAll();
	long countBooks();
	Book findByIsbn(String isbn);
	Page<Book> findByUser(long userId, Pageable pageable);
	Page<Book> findByYearAndUser(int year, long userId, 
			Pageable pageable);
	List<Integer> findDistinctYearsByUser(long userId);
	List<String> findDistinctFirstCharByUser(long userId);
}
