package be.joelv.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.joelv.entities.Book;
import be.joelv.entities.User;
import be.joelv.entities.UserBook;
import be.joelv.services.BookDataService;
import be.joelv.services.BookService;
import be.joelv.services.RegistrationService;
import be.joelv.services.UserBookService;
import be.joelv.services.UserService;

@Controller
@RequestMapping(path="/books", produces=MediaType.TEXT_HTML_VALUE)
class BookController {
	private static final String ADD_VIEW = "add";
	private static final String MY_BOOKS_VIEW = "mybooks";
	private static final String BOOK_DETAIL_VIEW = "bookdetail";
	private static final String REDIRECT_TO_MYBOOKS = 
			"redirect:/books/mybooks";
	private final RegistrationService registrationService;
	private final BookService bookService;
	private final BookDataService bookDataService;
	private final UserService userService;
	private final UserBookService userBookService;
	private final String bookDataURL;
	private final String apiKey;
	BookController(RegistrationService registrationService, BookService bookService, 
			BookDataService bookDataService, UserService userService, 
			UserBookService userBookService, 
			@Value ("${openLibraryData}") String bookDataURL, 
			@Value ("${apiKey}") String apiKey) {
		this.registrationService = registrationService;
		this.bookService = bookService;
		this.bookDataService = bookDataService;
		this.userService = userService;
		this.userBookService = userBookService;
		this.bookDataURL = bookDataURL;
		this.apiKey = apiKey;
	}
	@GetMapping("search")
	ModelAndView addBookForm() {
		String URL = bookDataURL + "key=" + apiKey + "&q=isbn:";
		return new ModelAndView(ADD_VIEW).addObject("bookUrl", URL);
	}
	@GetMapping("mybooks")
	ModelAndView myBooks(User user, Pageable pageable) {
		long userId = getUserId();
		Page<Book> page = bookService.findByUser(userId, pageable);
		return new ModelAndView(MY_BOOKS_VIEW).addObject("page", page);
	}
	@GetMapping(value="mybooks", params="year")
	ModelAndView myBooksFilteredByYear(int year, User user, Pageable pageable) {
		long userId = getUserId();
		Page<Book> page = bookService.findByYearAndUser(year, userId, pageable);
		return new ModelAndView(MY_BOOKS_VIEW).addObject("page", page);
	}
	@GetMapping(value="mybooks", params="title")
	ModelAndView myBooksFilteredByTitle(String title, User user, Pageable pageable) {
		long userId = getUserId();
		Page<Book> page = bookService.findByTitleandUser(title, userId, pageable);
		return new ModelAndView(MY_BOOKS_VIEW).addObject("page", page);
	}
	@GetMapping("{id}")
	ModelAndView read(@PathVariable long id) {
		Book book = bookService.read(id).get();
		return new ModelAndView(BOOK_DETAIL_VIEW).addObject("book", book);
	}
	@PostMapping(value="add", params="inputIsbn")
	String register(String inputIsbn) {
		long userId = getUserId();
		bookDataService.getBook(inputIsbn).ifPresent(book ->
				registrationService.register(userId, book));
		return REDIRECT_TO_MYBOOKS;
	}
	@PostMapping("mybooks/{id}/delete")
	public String unregister(@PathVariable long id) {
		long userId = getUserId();
		registrationService.unregister(userId, id);
	return REDIRECT_TO_MYBOOKS;
	}
	@InitBinder("isbnForm")
	void initBinderIsbn(WebDataBinder binder) {
	 binder.initDirectFieldAccess();
	}
	@InitBinder("book")
	void initBinderBook(WebDataBinder binder) {
	 binder.initDirectFieldAccess();
	}
	private long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return userService.read(username).get().getId();
	}
}