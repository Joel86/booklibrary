package be.joelv.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.UserBook;
import be.joelv.repositories.UserBookRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultUserBookService implements UserBookService {
	private final UserBookRepository UserBookRepository;
	DefaultUserBookService(UserBookRepository userBookRepository) {
		this.UserBookRepository = userBookRepository;
	}
	@Override
	public UserBook findByBookIdAndUserId(long bookId, long userId) {
		return UserBookRepository.findByBookIdAndUserId(bookId, userId);
	}
}