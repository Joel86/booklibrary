package be.joelv.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Author;
import be.joelv.entities.Book;
import be.joelv.entities.Genre;
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
			Book bookOutput;
			if(! bookService.findByIsbn(book.getIsbn10()).isPresent()) {
				bookOutput = new Book(book.getIsbn10(), book.getIsbn13(), book.getTitle(), 
						book.getPages(), book.getYear(), book.getThumbnailUrl());
				if(publisherService.findByName(book.getPublisher().getName()).isPresent()) {
					bookOutput.setPublisher(publisherService.findByName(book.getPublisher().getName()).get());
				} else {
					bookOutput.setPublisher(book.getPublisher());
				}
				for(Author author:book.getAuthors()) {
					if(authorService.findByNameAndSurname(author.getName(), author.getSurname()).isPresent()) {
						bookOutput.add(authorService.findByNameAndSurname(author.getName(), author.getSurname()).get());
					} else {
						bookOutput.add(author);
					}
				}
				for(Genre genre:book.getGenres()) {
					if(genreService.findByName(genre.getName()).isPresent()) {
						bookOutput.add(genreService.findByName(genre.getName()).get());
					} else {
						bookOutput.add(genre);
					}
				}
				bookService.create(bookOutput);
			} else {
				bookOutput = bookService.findByIsbn(book.getIsbn10()).get();
			}
			optionalUser.get().add(bookOutput);
		}
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void unregister(long userid, long bookid) {
		//todo
	}
}