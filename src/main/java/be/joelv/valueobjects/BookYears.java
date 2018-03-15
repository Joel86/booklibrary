package be.joelv.valueobjects;

import java.util.List;

public class BookYears {
	private List<Integer> years;
	public BookYears(List<Integer> years) {
		this.years = years;
	}
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
}