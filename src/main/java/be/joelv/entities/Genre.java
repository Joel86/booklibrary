package be.joelv.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "genres")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@SafeHtml
	@Length(min=1, max=20)
	@Column(unique=true)
	private String name;
	@ManyToMany(mappedBy = "genres", 
			cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@Valid
	private Set<Book> books = new LinkedHashSet<>();
	
	public Genre() {}
	public Genre(String name) {
		this.name = name;
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
	public void add(Book book) {
		books.add(book);
		if(!book.getGenres().contains(this)) {
			book.add(this);
		}
	}
	public void remove(Book book) {
		books.remove(book);
		if(book.getGenres().contains(this)) {
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
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Genre)) {
			return false;
		}
		Genre other = (Genre) obj;
		return name.equals(other.name);
	}
}