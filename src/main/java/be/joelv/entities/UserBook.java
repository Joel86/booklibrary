package be.joelv.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_books")
public class UserBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userBookId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Book book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	private boolean read;
	
	public UserBook() {}
	public UserBook(Book book, User user, boolean read) {
		this.book = book;
		this.user = user;
		this.read = read;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		if(this.book != null && this.book.getUsers().contains(this)) {
			this.book.remove(this);
		}
		this.book = book;
		if(book != null && ! book.getUsers().contains(this)) {
			book.add(this);
		}
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		if(this.user != null && this.user.getBooks().contains(this)) {
			this.user.remove(this);
		}
		this.user = user;
		if(user != null && ! user.getBooks().contains(this)) {
			user.add(this);
		}
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserBook))
			return false;
		UserBook other = (UserBook) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}