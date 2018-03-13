package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Author;
import be.joelv.repositories.AuthorRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultAuthorService implements AuthorService {
	private final AuthorRepository authorRepository;
	DefaultAuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Author author) {
		authorRepository.save(author);
	}

	@Override
	public Optional<Author> read(long id) {
		return Optional.ofNullable(
				authorRepository.findOne(id));
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Author author) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(Author author) {
		if(author.getBooks().isEmpty()) {
			authorRepository.delete(author);
		}
	}

	@Override
	public List<Author> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countAuthors() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Author findByNameAndSurname(String name, String surname) {
		return authorRepository.findByNameAndSurname(name, surname);
	}
}