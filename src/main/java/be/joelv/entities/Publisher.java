package be.joelv.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "publishers")
public class Publisher implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@SafeHtml
	@Column(unique=true)
	private String name;
	@OneToMany(mappedBy = "publisher", orphanRemoval=true)
	private Set<Book> books;
	
	public Publisher() {}
	public Publisher(String name) {
		this.name = name;
		books = new LinkedHashSet<>();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Book> getBooks() {
		return Collections.unmodifiableSet(books);
	}
	public void add(Book book) {
		books.add(book);
		if(book.getPublisher() != this) {
			book.setPublisher(this);
		}
	}
	public void remove(Book book) {
		books.remove(book);
		if(book.getPublisher() == this) {
			book.setPublisher(null);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Publisher)) {
			return false;
		}
		Publisher other = (Publisher) obj;
		return name.equals(other.name);
	}
}