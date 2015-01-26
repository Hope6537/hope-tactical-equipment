package org.hope6537.note.thinking_in_java.seventeen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class StringAddress {
	private String s;
	public StringAddress() {
	}
	public StringAddress(String s) {
		super();
		this.s = s;
	}
	@Override
	public String toString() {
		return "StringAddress [s=" + s + "]";
	}
	
}

public class FillngLists {

	public static void main(String[] args) {
		List<StringAddress> list = new ArrayList<StringAddress>(Collections.nCopies(4, new StringAddress("Tar")));
		System.out.println(list);
	} 
}
