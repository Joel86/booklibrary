package be.joelv.services;

import be.joelv.entities.Book;

public interface RegistrationService {
	void register(long userid, Book book);
	void unregister(long userid, long bookid);
}
