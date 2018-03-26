package be.joelv.services;

import be.joelv.entities.UserBook;

public interface UserBookService {
	UserBook findByBookIdAndUserId(long bookId, long userId);
	void delete(UserBook userBook);
}
