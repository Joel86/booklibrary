package be.joelv.services;

import org.springframework.stereotype.Service;

import be.joelv.entities.Book;
import be.joelv.restclients.RestClient;
import be.joelv.valueobjects.IsbnForm;

@Service
class BookDataServiceImpl implements BookDataService {
	private final RestClient restClient;
	BookDataServiceImpl(RestClient restClient) {
		this.restClient = restClient;
	}
	@Override
	public Book getBook(IsbnForm isbn) {
		return restClient.getBookData(isbn);
	}

}
