package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.Publisher;
import be.joelv.repositories.PublisherRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultPublisherService implements PublisherService {
	private final PublisherRepository publisherRepository;
	DefaultPublisherService(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Publisher publisher) {
		if(!findByName(publisher.getName()).isPresent()) {
			publisherRepository.save(publisher);
		}
	}

	@Override
	public Optional<Publisher> read(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Publisher publisher) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(Publisher publisher) {
		if(publisher.getBooks().isEmpty()) {
			publisherRepository.delete(publisher);
		}
	}

	@Override
	public List<Publisher> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countPublishers() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Optional<Publisher> findByName(String name) {
		return Optional.ofNullable(publisherRepository.findByName(name));
	}
}