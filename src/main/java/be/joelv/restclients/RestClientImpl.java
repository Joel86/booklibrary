package be.joelv.restclients;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import be.joelv.entities.Author;
import be.joelv.entities.Book;
import be.joelv.entities.Genre;
import be.joelv.entities.Publisher;
import be.joelv.exceptions.BookNotFoundException;
import be.joelv.exceptions.CouldntReadBookDataException;

@Component
class RestClientImpl implements RestClient {
	private final String bookDataURL;
	private final RestTemplate restTemplate;
	private String apiKey;
	private final static Logger LOGGER =
			 Logger.getLogger(RestClientImpl.class.getName());
	RestClientImpl(@Value ("${openLibraryData}") String bookDataURL, 
			@Value ("${apiKey}") String apiKey, RestTemplate restTemplate) {
		this.bookDataURL = bookDataURL;
		this.restTemplate = restTemplate;
		this.apiKey = apiKey;
	}
	@Override
	public Book getBookData(String isbn) {
		try {
			URI targetUrl= UriComponentsBuilder.fromUriString(bookDataURL)
			    .queryParam("q", "isbn=" + isbn)
			    .queryParam("key", apiKey)
			    .build()
			    .encode()
			    .toUri();
			if(restTemplate.getForObject(targetUrl, Result.class).totalItems == 0) {
				throw new BookNotFoundException();
			}
			Result result = restTemplate.getForObject(targetUrl, Result.class);
			VolumeInfo volumeInfo = result.items.get(0).volumeInfo;
			String title = volumeInfo.title;
			int year = Integer.parseInt(volumeInfo.publishedDate.substring(0,4));
			int pages = volumeInfo.pageCount;
			byte posIsbn13 = 1;
			byte posIsbn10 = 0;
			if(volumeInfo.industryIdentifiers.get(0).type.equals("ISBN_13")) {
				posIsbn13 = 0;
				posIsbn10 = 1;
			}
			String isbn10 = volumeInfo.industryIdentifiers.get(posIsbn10).identifier;
			String isbn13 = volumeInfo.industryIdentifiers.get(posIsbn13).identifier;
			String thumbnailUrl = volumeInfo.imageLinks.smallThumbnail;
			Publisher publisher = new Publisher(volumeInfo.publisher);
			Book book = new Book(isbn10, isbn13, title, pages, year, thumbnailUrl);
			book.setPublisher(publisher);
			if(volumeInfo.authors != null) {
				for(String authorStr : volumeInfo.authors) {
					String firstName = authorStr.substring(0, authorStr.indexOf(" "));
					String lastName = authorStr.substring(authorStr.indexOf(" ") + 1);
					book.add(new Author(firstName, lastName));
				}
			}
			if(volumeInfo.categories != null) {
				for(String categoryStr : volumeInfo.categories) {
					book.add(new Genre(categoryStr));
				}
			}
			System.out.println(targetUrl);
			return book;
		} catch(RestClientException ex) {
			LOGGER.log(Level.SEVERE, "Unable to read book data", ex);
			throw new CouldntReadBookDataException();
		}
	}
}