package be.joelv.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import be.joelv.exceptions.BookNotFoundException;
import be.joelv.exceptions.CouldntReadBookDataException;
import be.joelv.services.BookDataService;
import be.joelv.services.BookService;
import be.joelv.services.RegistrationService;
import be.joelv.services.UserService;
import be.joelv.valueobjects.IsbnForm;

@Controller
@RequestMapping("/books")
class BookController {
	private static final String ADD_VIEW = "add";
	private static final String MY_BOOKS_VIEW = "mybooks";
	private static final String BOOK_DETAIL_VIEW = "bookdetail";
	private static final String REDIRECT_AFTER_ADDING = 
			"redirect:/books/mybooks";
	private final RegistrationService registrationService;
	private final BookService bookService;
	private final BookDataService bookDataService;
	private final UserService userService;
	BookController(RegistrationService registrationService, BookService bookService, 
			BookDataService bookDataService, UserService userService) {
		this.registrationService = registrationService;
		this.bookService = bookService;
		this.bookDataService = bookDataService;
		this.userService = userService;
	}
	@GetMapping("search")
	ModelAndView addBookForm() {
		IsbnForm isbnForm = new IsbnForm();
		return new ModelAndView(ADD_VIEW).addObject(isbnForm);
	}
	@GetMapping (value="search", params="isbn")
	ModelAndView addBook(IsbnForm isbn) {
		ModelAndView modelAndView = new ModelAndView(ADD_VIEW);
		try {
			modelAndView.addObject("book", bookDataService.getBook(isbn));
		} catch(BookNotFoundException ex) {
			modelAndView.addObject("error", "Book not found");
		} catch(CouldntReadBookDataException ex) {
			modelAndView.addObject("error", "Could not read book data");
		}
		return modelAndView;
	}
	@GetMapping("mybooks")
	ModelAndView myBooks(User user, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Page<Book> page = bookService.findByUser(username, pageable);
		return new ModelAndView(MY_BOOKS_VIEW).addObject("page", page);
	}
	@GetMapping("{id}")
	ModelAndView read(@PathVariable long id) {
		Book book = bookService.read(id).get();
		return new ModelAndView(BOOK_DETAIL_VIEW).addObject("book", book);
	}
	@PostMapping("add")
	String register(Book book) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		long userId = userService.read(username).get().getId();
		registrationService.register(userId, book);
		return REDIRECT_AFTER_ADDING;
	}
	@InitBinder("isbnForm")
	void initBinderIsbn(WebDataBinder binder) {
	 binder.initDirectFieldAccess();
	}
	@InitBinder("book")
	void initBinderBook(WebDataBinder binder) {
	 binder.initDirectFieldAccess();
	}
}