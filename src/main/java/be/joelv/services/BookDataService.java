package be.joelv.services;

import java.util.Optional;

import be.joelv.entities.Book;
import be.joelv.valueobjects.IsbnForm;

public interface BookDataService {
	Optional<Book> getBook(String isbn);
}
