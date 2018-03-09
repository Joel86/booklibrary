package be.joelv.services;

import be.joelv.entities.Book;

public interface RegistrationService {
	void register(long userid, String isbn);
	void delete(long userid, long bookid);
}
