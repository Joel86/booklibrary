package be.joelv.services;

import be.joelv.entities.Book;
import be.joelv.valueobjects.IsbnForm;

public interface BookDataService {
	Book getBook(IsbnForm isbn);
}
