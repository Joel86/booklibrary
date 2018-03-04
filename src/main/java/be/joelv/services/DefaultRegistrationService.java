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
			GenreService genreService, PublisherService publisherService) {
		this.userService = userService;
		this.bookService = bookService;
		this.authorService = authorService;
		this.genreService = genreService;
		this.publisherService = publisherService;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void register(long userid, Book restBook) {
		Optional<User> optionalUser = userService.read(userid);
		if(optionalUser.isPresent()) {
			Book book = new Book(restBook.getIsbn10(), restBook.getIsbn13(), restBook.getTitle(), 
					restBook.getPages(), restBook.getYear(), restBook.getThumbnailUrl());
			if(!bookService.findByIsbn(restBook.getIsbn10()).isPresent()) {
				for(Author author : restBook.getAuthors()) {
					if(authorService.findByNameAndSurname(author.getName(), author.getSurname()).isPresent()) {
						book.add(authorService.findByNameAndSurname(author.getName(), author.getSurname()).get());
					} else {
						authorService.create(author);
						book.add(author);
					}
				}
				for(Genre genre : restBook.getGenres()) {
					if(genreService.findByName(genre.getName()).isPresent()) {
						book.add(genreService.findByName(genre.getName()).get());
					} else {	
						genreService.create(genre);
						book.add(genre);
					}
				}
				if(restBook.getPublisher() != null) {
					String publisherName = restBook.getPublisher().getName();
					if(publisherService.findByName(publisherName).isPresent()) {
						book.setPublisher(publisherService.findByName(publisherName).get());
					} else {
						Publisher publisher = new Publisher(publisherName);
						publisherService.create(publisher);
						book.setPublisher(publisher);
					}
				}
				bookService.create(book);
			} else {
				book = bookService.findByIsbn(restBook.getIsbn10()).get();
			}
			optionalUser.get().add(book);
		}
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(long userid, long bookid) {
		//todo
	}
}