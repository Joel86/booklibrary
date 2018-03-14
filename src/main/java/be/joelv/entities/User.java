package be.joelv.entities;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@SafeHtml
	@NotBlank
	@Length(min=1, max=20)
	@Column(unique=true)
	private String username;
	@SafeHtml
	@NotBlank
	@Length(min=6, max=20)
	private String password;
	@ManyToMany(mappedBy = "users", 
			cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@Valid
	private Set<Book> books = new LinkedHashSet<>();
	public User() {}
	public User(long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
		books = new LinkedHashSet<>();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void add(Book book) {
		books.add(book);
		if(!book.getUsers().contains(this)) {
			book.add(this);
		}
	}
	public void remove(Book book) {
		books.remove(book);
		if(book.getUsers().contains(this)) {
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
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return username.equals(other.username);
	}
	
}