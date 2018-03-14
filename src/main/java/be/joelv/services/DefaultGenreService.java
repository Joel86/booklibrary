package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Genre;
import be.joelv.repositories.GenreRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultGenreService implements GenreService {
	private final GenreRepository genreRepository;
	DefaultGenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public Optional<Genre> read(long id) {
		return Optional.ofNullable(
				genreRepository.findOne(id));
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Genre genre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(Genre genre) {
		genreRepository.delete(genre);
	}
	@Override
	public List<Genre> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countGenres() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Genre findByName(String name) {
		return genreRepository.findByName(name);
	}
}