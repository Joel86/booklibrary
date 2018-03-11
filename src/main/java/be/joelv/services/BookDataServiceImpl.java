package be.joelv.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import be.joelv.entities.Book;
import be.joelv.restclients.RestClient;

@Service
class BookDataServiceImpl implements BookDataService {
	private final RestClient restClient;
	BookDataServiceImpl(RestClient restClient) {
		this.restClient = restClient;
	}
	@Override
	public Optional<Book> getBook(String isbn) {
		return Optional.ofNullable(
				restClient.getBookData(isbn));
	}

}
