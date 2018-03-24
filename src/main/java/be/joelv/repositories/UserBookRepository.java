package be.joelv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.joelv.entities.UserBook;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
	UserBook findByBookIdAndUserId(long bookId, long userId);
}
