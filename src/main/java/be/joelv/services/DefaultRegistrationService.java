package be.joelv.services;

import java.util.ArrayList;
import java.util.List;
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
			
			List<Author> authorsList = new ArrayList<>(book.getAuthors());
			for(int i=0;i<authorsList.size();i++) {
				Author author = authorService.findByNameAndSurname(
					authorsList.get(i).getName(), authorsList.get(i).getSurname());
				if(author == null) {
					author = new Author(authorsList.get(i).getName(), authorsList.get(i).getSurname());
				}
				bookOutput.add(author);
			}
			
			List<Genre> genreList = new ArrayList<>(book.getGenres());
			for(int i=0;i<genreList.size();i++) { 
				Genre genre = genreService.findByName(
					genreList.get(i).getName());
				if(genre == null) {
					genre = new Genre(genreList.get(i).getName());
				}
				bookOutput.add(genre);
			}
			bookOutput.add(optionalUser.get());
		}
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void unregister(long userid, long bookid) {
		//todo
	}
}