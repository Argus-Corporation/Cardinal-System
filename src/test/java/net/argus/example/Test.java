package net.argus.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class Test {
	
	public static void main(String[] args) throws IOException {
		/*writeOutput("\u00e9", "D:\\Django\\bureau 1\\test.txt");
		System.out.println(readInput("D:\\Django\\bureau 1\\test.txt"));*/
		
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\Django\\bureau 1\\test.txt"))));
		String str;
		while((str = read.readLine()) != null)
			System.out.println(str);
	}
	
	private static String readInput(String file) {
		 
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			Reader in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);
			}
			in.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static void writeOutput(String str, String file) {
		 
		try {
			FileOutputStream fos = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(fos, "UTF8");
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
