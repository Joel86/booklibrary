package be.joelv.services;

import be.joelv.entities.Book;

public interface RegistrationService {
	
	/**Adds book to user library
	 * Adds book, publisher, author(s) and genre(s)
	 * to corresponding tables if not exist
	 * @param userid the user id
	 * @param book book entity to be added to user library
	 */
	void register(long userid, Book book);
	
	/**Deletes book from user library
	 * Deletes book from books table if no relation with users
	 * Deletes publisher, author(s) and genre(s) from corresponding
	 * tables if no relation with books  
	 * @param userid the user id
	 * @param id the id of the book to be removed from user library
	 */
	void unregister(long userid, long id);
}
