package be.joelv.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Author;
import be.joelv.entities.Book;
import be.joelv.entities.Genre;
import be.joelv.entities.Publisher;
import be.joelv.entities.User;

@Service
class DefaultRegistrationService implements RegistrationService {
	private final UserService userService;
	private final BookService bookService;
	private final AuthorService authorService;
	private final GenreService genreService;
	private final PublisherService publisherService;
	DefaultRegistrationService(UserService userService, BookService bookService, AuthorService authorService,
			GenreService genreService, PublisherService publisherService, BookDataService bookDataService) {
		this.userService = userService;
		this.bookService = bookService;
		this.authorService = authorService;
		this.genreService = genreService;
		this.publisherService = publisherService;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void register(long userid, Book book) {
		Optional<User> optionalUser = userService.read(userid);
		if(optionalUser.isPresent()) {
			Book bookOutput = bookService.findByIsbn(book.getIsbn10());
			if(bookOutput == null) {
				bookOutput = new Book(book.getIsbn10(), book.getIsbn13(), book.getTitle(), book.getPages(), 
						book.getYear(), book.getThumbnailUrl());
			}
			
			Publisher publisher = publisherService.findByName(book.getPublisher().getName());
			if(publisher == null) {
				publisher = new Publisher(book.getPublisher().getName());
			}
			bookOutput.setPublisher(publisher);
			
			book.getAuthors().stream()
				.forEach(author -> 
					{Author authorr = authorService.findByNameAndSurname(
						author.getName(), author.getSurname());
					if(authorr == null) {
						authorr = new Author(author.getName(), author.getSurname());
					}
					bookOutput.add(authorr);
					});
			
			book.getGenres().stream()
				.forEach(genre -> 
					{Genre genree = genreService.findByName(
						genre.getName());
					if(genree == null) {
						genree = new Genre(genre.getName());
					}
					bookOutput.add(genree);
					});
			
			bookOutput.add(optionalUser.get());
		}
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void unregister(long userid, long bookid) {
		//todo
	}
}