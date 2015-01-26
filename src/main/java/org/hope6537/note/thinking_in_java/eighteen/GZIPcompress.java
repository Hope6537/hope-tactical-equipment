package org.hope6537.note.thinking_in_java.eighteen;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @describe 浣跨敤GZIP妯″紡杩涜鍘嬬缉
 * @author Hope6537(璧甸箯)
 * @signdate 2014骞�鏈�5鏃ヤ笅鍗�2:48:24
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class GZIPcompress {

	public static void main(String[] args) throws IOException {

		Pcompress(new String[] { "G:\\Article.txt" });
	}

	@SuppressWarnings("resource")
	public static void Pcompress(String[] args) throws IOException {

		File file = new File("G:\\Test.gz");
		if (args.length == 0) {
			System.out.println("No Args");
			System.exit(1);
		}
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		BufferedOutputStream out = new BufferedOutputStream(
				new GZIPOutputStream(new FileOutputStream(file)));
		System.out.println("Writing File");
		int c;
		while ((c = in.read()) != -1) {
			out.write(c);
		}
		in.close();
		out.close();
		System.out.println("Reading File");
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
				new GZIPInputStream(new FileInputStream(file))));
		String s;
		while ((s = in2.readLine()) != null) {
			System.out.println(s);
		}

	}
}
