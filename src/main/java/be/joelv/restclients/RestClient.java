package be.joelv.restclients;

import be.joelv.entities.Book;

public interface RestClient {
	Book getBookData(String isbn);
}
