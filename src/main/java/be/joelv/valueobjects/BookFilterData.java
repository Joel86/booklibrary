package be.joelv.valueobjects;

import java.util.List;

public class BookFilterData {
	private List<Integer> years;
	private List<String> chars;
	public BookFilterData(List<Integer> years, List<String> chars) {
		this.years = years;
		this.chars = chars;
	}
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	public List<String> getChars() {
		return chars;
	}
	public void setChars(List<String> chars) {
		this.chars = chars;
	}
}