package be.joelv.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.UserBook;
import be.joelv.repositories.UserBookRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultUserBookService implements UserBookService {
	private final UserBookRepository userBookRepository;
	DefaultUserBookService(UserBookRepository userBookRepository) {
		this.userBookRepository = userBookRepository;
	}
	@Override
	public UserBook findByBookIdAndUserId(long bookId, long userId) {
		return userBookRepository.findByBookIdAndUserId(bookId, userId);
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(UserBook userBook) {
		userBookRepository.delete(userBook);
	}
}