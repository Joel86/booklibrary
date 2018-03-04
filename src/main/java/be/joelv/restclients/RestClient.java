package be.joelv.restclients;

import be.joelv.entities.Book;
import be.joelv.valueobjects.IsbnForm;

public interface RestClient {
	Book getBookData(IsbnForm isbn);
}
