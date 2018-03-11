package be.joelv.services;

import java.util.Optional;

import be.joelv.entities.Book;

public interface BookDataService {
	Optional<Book> getBook(String isbn);
}
