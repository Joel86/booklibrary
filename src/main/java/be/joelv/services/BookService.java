package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.joelv.entities.Author;
import be.joelv.entities.Book;
import be.joelv.entities.Genre;
import be.joelv.entities.Publisher;


public interface BookService {
	public void create(Book book);
	Optional<Book> read(long id);
	void update(Book book);
	void delete(Book book);
	List<Book> findAll();
	long countBooks();
	void addAuthor(long id, Author author);
	void addGenre(long id, Genre genre);
	void setPublisher(long id, Publisher publisher);
	Book findByIsbn(String isbn);
	Page<Book> findByUser(String username, Pageable pageable);
	Page<Book> findByYearAndUser(int year, String username, 
			Pageable pageable);
	List<Integer> findDistinctYearsByUser(String username);
}
