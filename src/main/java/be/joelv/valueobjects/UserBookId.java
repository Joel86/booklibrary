package be.joelv.valueobjects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserBookId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "bookId")
	private long bookId;
	@Column(name = "userId")
	private long userId;
	public UserBookId() {}
	public UserBookId(long bookId, long userId) {
		this.bookId = bookId;
		this.userId = userId;
	}
	public long getBookId() {
		return bookId;
	}
	public long getUserId() {
		return userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserBookId))
			return false;
		UserBookId other = (UserBookId) obj;
		if (bookId != other.bookId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
}