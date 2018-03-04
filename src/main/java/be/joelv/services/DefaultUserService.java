package be.joelv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.joelv.entities.User;
import be.joelv.repositories.UserRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultUserService implements UserService {
	private final UserRepository userRepository;
	DefaultUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(User user) {
		userRepository.save(user);
	}

	@Override
	public Optional<User> read(long id) {
		return Optional.ofNullable(
			userRepository.findOne(id));
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countUsers() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Optional<User> read(String username) {
		return Optional.ofNullable(
			userRepository.findByUsername(username));
	}
	
}