package be.joelv.valueobjects;

import java.util.List;

public class BookStartingChars {
	private List<String> chars;
	public BookStartingChars(List<String> chars) {
		this.chars = chars;
	}
	public List<String> getChars() {
		return chars;
	}
	public void setChars(List<String> chars) {
		this.chars = chars;
	}
}
