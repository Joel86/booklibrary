package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Author;
import be.joelv.entities.Book;
import be.joelv.entities.Genre;
import be.joelv.entities.Publisher;
import be.joelv.repositories.BookRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultBookService implements BookService {
	private final BookRepository bookRepository;
	DefaultBookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Book book) {
		bookRepository.save(book);
	}

	@Override
	public Optional<Book> read(long id) {
		return Optional.ofNullable(
				bookRepository.findOne(id));
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(Book book) {
		if(book.getUsers().isEmpty()) {
			bookRepository.delete(book);
		}
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public long countBooks() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void addAuthor(long id, Author author) {
		Optional.ofNullable(bookRepository.findOne(id)).ifPresent(
				book -> book.add(author));
	}
	@Override
	public void addGenre(long id, Genre genre) {
		Optional.ofNullable(bookRepository.findOne(id)).ifPresent(
				book -> book.add(genre));
	}
	@Override
	public void setPublisher(long id, Publisher publisher) {
		Optional.ofNullable(bookRepository.findOne(id)).ifPresent(
				book -> book.setPublisher(publisher));
	}
	@Override
	public Optional<Book> findByIsbn(String isbn) {
		return Optional.ofNullable(
				bookRepository.findByIsbn10(isbn));
	}
	@Override
	public Page<Book> findByUser(String username, Pageable pageable) {
		return bookRepository.findByUsers_Username(username, pageable);
	}
}