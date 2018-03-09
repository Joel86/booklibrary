package be.joelv.restclients;

import java.util.Optional;

import be.joelv.entities.Book;
import be.joelv.valueobjects.IsbnForm;

public interface RestClient {
	Book getBookData(String isbn);
}
