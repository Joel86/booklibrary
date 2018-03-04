package be.joelv.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name="authors")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@SafeHtml
	@NotBlank
	@Length(min = 1, max = 50)
	private String name;
	@SafeHtml
	@NotBlank
	@Length(min = 1, max = 50)
	private String surname;
	@ManyToMany(mappedBy = "authors", 
			cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@Valid
	private Set<Book> books = new LinkedHashSet<>();
	
	public Author() {}
	public Author(String name, String surname) {
		this.name = name;
		this.surname = surname;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void add(Book book) {
		books.add(book);
		if(!book.getAuthors().contains(this)) {
			book.add(this);
		}
	}
	public void remove(Book book) {
		books.remove(book);
		if(book.getAuthors().contains(this)) {
			book.remove(this);
		}
	}
	public Set<Book> getBooks() {
		return Collections.unmodifiableSet(books);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Author)) {
			return false;
		}
		return ((Author) obj).name.equalsIgnoreCase(this.name) && 
				((Author) obj).surname.equalsIgnoreCase(this.surname);
	}
	@Override
	public String toString() {
		return surname + " " + name;
	}
}