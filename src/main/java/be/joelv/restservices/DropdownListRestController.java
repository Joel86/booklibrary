package be.joelv.restservices;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.joelv.entities.User;
import be.joelv.services.BookService;
import be.joelv.services.UserService;
import be.joelv.valueobjects.BookStartingChars;
import be.joelv.valueobjects.BookYears;

@RestController
@RequestMapping("/books/mybooks")
class DropdownListRestController {
	private final BookService bookService;
	private final UserService userService;
	DropdownListRestController(BookService bookService, UserService userService) {
		this.bookService = bookService;
		this.userService = userService;
	}
	@GetMapping("years")
	public BookYears getBookYearsByUser() {
		List<Integer> years = bookService.findDistinctYearsByUser(getUserId());
		return new BookYears(years);
	}
	@GetMapping("startingChars")
	public BookStartingChars getBookStartingCharsByUser() {
		List<String> chars = bookService.findDistinctFirstCharByUser(getUserId());
		return new BookStartingChars(chars);
	}
	private long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return userService.read(username).get().getId();
	}
}