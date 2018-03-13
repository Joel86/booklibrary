package be.joelv.services;

import java.util.List;

import be.joelv.entities.Book;

public interface RegistrationService {
	void register(long userid, Book book);
	void unregister(long userid, List<Long> ids);
}
