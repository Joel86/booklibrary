package be.joelv.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@NamedEntityGraphs({
	@NamedEntityGraph(name="Book.withAuthors", 
		attributeNodes=@NamedAttributeNode("authors")),
	@NamedEntityGraph(name="Book.withPublisher", 
		attributeNodes=@NamedAttributeNode("publisher")),
	@NamedEntityGraph(name="Book.withGenres", 
		attributeNodes=@NamedAttributeNode("genres"))
})
@Table(name = "books")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@SafeHtml
	@NotBlank
	@Column(unique=true)
	private String isbn10;
	@SafeHtml
	@NotBlank
	private String isbn13;
	@SafeHtml
	@NotBlank
	@Length(min=1, max=100)
	private String title;
	@NotNull
	private int pages;
	@NotNull
	private int year;
	@SafeHtml
	private String thumbnailUrl;
	@ManyToOne(fetch = FetchType.LAZY, optional=true, cascade=CascadeType.ALL)
	@JoinColumn(name = "publisherId")
	private Publisher publisher;
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
		name = "book_authors", 
		joinColumns = @JoinColumn(name = "bookId"), 
		inverseJoinColumns = @JoinColumn(name = "authorId"))
	@Valid
	private Set<Author> authors = new LinkedHashSet<>();
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
		name = "book_genres", 
		joinColumns = @JoinColumn(name = "bookId"), 
		inverseJoinColumns = @JoinColumn(name = "genreId"))
	@Valid
	private Set<Genre> genres = new LinkedHashSet<>();
	@OneToMany(
			mappedBy = "book", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private Set<UserBook> users = new LinkedHashSet<>();
	public Book() {}
	public Book(String isbn10, String isbn13, String title, 
			int pages, int year, String thumbnailUrl) {
		this.isbn10 = isbn10;
		this.isbn13 = isbn13;
		this.title = title;
		this.pages = pages;
		this.year = year;
		this.thumbnailUrl = thumbnailUrl;
		authors = new LinkedHashSet<>();
		genres = new LinkedHashSet<>();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIsbn10() {
		return isbn10;
	}
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}
	public String getIsbn13() {
		return isbn13;
	}
	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		if(this.publisher != null && this.publisher.getBooks().contains(this)) {
			this.publisher.remove(this);
		}
		this.publisher = publisher;
		if(publisher != null && !publisher.getBooks().contains(this)) {
			publisher.add(this);
		}
	}
	public void add(Author author) {
		authors.add(author);
		if(!author.getBooks().contains(this)) {
			author.add(this);
		}
	}
	public void remove(Author author) {
		authors.remove(author);
		if(author.getBooks().contains(this)) {
			author.remove(this);
		}
	}
	public Set<Author> getAuthors() {
		return Collections.unmodifiableSet(authors);
	}
	public void add(Genre genre) {
		genres.add(genre);
		if(!genre.getBooks().contains(this)) {
			genre.add(this);
		}
	}
	public void remove(Genre genre) {
		genres.remove(genre);
		if(genre.getBooks().contains(this)) {
			genre.remove(this);
		}
	}
	public Set<Genre> getGenres() {
		return Collections.unmodifiableSet(genres);
	}
	public void add(UserBook userBook) {
		users.add(userBook);
		if(userBook.getBook() != this) {
			userBook.setBook(this);
		}
	}
	public void remove(UserBook userBook) {
		users.remove(userBook);
		if(userBook.getBook() == this) {
			userBook.setBook(null);
		}
	}
	public Set<UserBook> getUsers() {
		return Collections.unmodifiableSet(users);
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn10 == null) ? 0 : isbn10.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		return isbn10.equals(other.isbn10);
	}
}