package org.hope6537.note.thinking_in_java.eighteen;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DirList {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		printPath(s.next());
	}

	public static void printPath(final String arg) {

		File path = new File(".");
		String[] list;
		if (arg != null && !arg.isEmpty()) {
			list = path.list(new FilenameFilter() {
				private Pattern pattern = Pattern.compile(arg);

				@Override
				public boolean accept(File dir, String name) {
					return pattern.matcher(name).matches();
				}
			});
		} else {
			list = path.list();
		}
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list) {
			System.out.println(dirItem);
		}

	}
}
