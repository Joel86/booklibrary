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
	private final BookDataService bookDataService;
	DefaultRegistrationService(UserService userService, BookService bookService, AuthorService authorService,
			GenreService genreService, PublisherService publisherService, BookDataService bookDataService) {
		this.userService = userService;
		this.bookService = bookService;
		this.authorService = authorService;
		this.genreService = genreService;
		this.publisherService = publisherService;
		this.bookDataService = bookDataService;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void register(long userid, String isbn) {
		Optional<User> optionalUser = userService.read(userid);
		if(optionalUser.isPresent()) {		
			Optional<Book> optionalBook = bookService.findByIsbn(isbn);
			if(optionalBook.isPresent()) {	
				Book book = optionalBook.get();
			} else {
				optionalBook = bookDataService.getBook(isbn);
				if(optionalBook.isPresent()) {
					Book book = optionalBook.get();
					optionalUser.get().add(book);
					for(Author author : book.getAuthors()) {
						Optional<Author> optionalAuthor = authorService.findByNameAndSurname(
								author.getName(), author.getSurname());
						if(optionalAuthor.isPresent()) {
							book.add(optionalAuthor.get());
						} else {
							authorService.create(author);
							book.add(author);
						}
					}
					for(Genre genre : book.getGenres()) {
						Optional<Genre> optionalGenre = genreService.findByName(genre.getName());
						if(optionalGenre.isPresent()) {
							book.add(optionalGenre.get());
						} else {	
							genreService.create(genre);
							book.add(genre);
						}
					}
					Optional<Publisher> optionalPublisher = publisherService.findByName(
							book.getPublisher().getName());
					if(optionalPublisher.isPresent()) {
						book.setPublisher(optionalPublisher.get());
					} else {
						Publisher publisher = book.getPublisher();
						publisherService.create(publisher);
						book.setPublisher(publisher);
					}
					bookService.create(book);
					optionalUser.get().add(book);
				}
			}
		}
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(long userid, long bookid) {
		//todo
	}
}