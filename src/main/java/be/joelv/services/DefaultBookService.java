package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Book;
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
		bookRepository.delete(book);
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll(new Sort("title"));
	}

	@Override
	public long countBooks() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Book findByIsbn(String isbn) {
		return bookRepository.findByIsbn10(isbn);
	}
	@Override
	public Page<Book> findByUser(long userId, Pageable pageable) {
		return bookRepository.findByUserId(userId, pageable);
	}
	@Override
	public Page<Book> findByYearAndUser(int year, long userId, Pageable pageable) {
		return bookRepository.findByYearAndUsers_UserId(year, userId, pageable);
	}
	@Override
	public List<Integer> findDistinctYearsByUser(long userId) {
		return bookRepository.findDistinctYearsByUser(userId);
	}
	@Override
	public List<String> findDistinctFirstCharByUser(long userId) {
		return bookRepository.findDistinctFirstCharByUser(userId);
	}
}