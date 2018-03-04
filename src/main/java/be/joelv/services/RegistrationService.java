package be.joelv.services;

import be.joelv.entities.Book;

public interface RegistrationService {
	void register(long userid, Book book);
	void delete(long userid, long bookid);
}
