package be.joelv.restservices;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.joelv.entities.User;
import be.joelv.services.BookService;
import be.joelv.valueobjects.BookYears;

@RestController
@RequestMapping("/books/mybooks")
class DropdownListRestController {
	private final BookService bookService;
	DropdownListRestController(BookService bookService) {
		this.bookService = bookService;
	}
	@GetMapping("years")
	public BookYears getBookYears(User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Integer> years = bookService.findDistinctYearsByUser(username);
		return new BookYears(years);
	}
}