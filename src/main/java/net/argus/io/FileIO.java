package net.argus.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.argus.file.Properties;
import net.argus.instance.Instance;
import net.argus.util.ArrayManager;

public class FileIO {
	
	/**--READ--**/
	public static char[] readChar(File file) throws IOException {
		DataInputStream in = openInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	//	byte[] data = new byte[(int) file.length()];
		//in.read(data);
		
		//reader.skip(50);
		//System.out.println(reader.readLine());
		char[] data = new char[(int) file.length()];

		reader.read(data);
		
		//System.out.println(data);
		
		//System.out.println(new String(datr));
		in.close();
		
		return data;
	}
	
	public static String[] readArray(File file) throws IOException {
		String regex = "";
		
		String str = readString(file);
		if(str.contains("\r"))
			regex += "\r";
		if(str.contains("\n"))
			regex += "\n";
			
		String[] data = str.split(regex);
		for(int i = 0; i < data.length; i++) {
			int index = data[i].length();
			if(index > -1)
				data[i] = data[i].substring(0, index);
			
		}
		
		return data;
	}
	
	public static String readString(File file) throws IOException {
		String str = valueOf(readChar(file));
		return str;
	}
	
	public static String readLine(File file, int line) throws IOException {
		String strLine = readArray(file)[line];
		return strLine;
	}
	
	/**--WRITE--**/
	public static void write(DataOutputStream out, byte[] data) throws IOException {
		out.write(data);
		out.close();
	}
	
	public static void write(File file, byte[] data) throws IOException {
		write(openOutputStream(file), data);
	}
	
	public static void write(File file, String text) throws IOException {
		write(file, text.getBytes());
	}
	
	public static void write(File file, String[] textArray) throws IOException {
		String text = "";
		for(String str : textArray)
			text += str + "\n";
			
		if(text.length() > 1)
			text = text.substring(0, text.length() -1);
		
		write(file, text);
	}
	
	public static void write(File file, String text, int line) throws IOException {
		List<String> lines = ArrayManager.convert(readArray(file));
		
		lines.add(lines.get(lines.size()-1));
		for(int i = lines.size() - 1; i > line; i--)
			lines.set(i, lines.get(i - 1));
		lines.set(line, text);

		write(file, (String[]) lines.toArray(new String[lines.size()]));
	}
	
	
	public static void writeAppend(File file, byte[] data) throws IOException {
		write(openOutputStream(file, true), data);
	}
	
	public static void writeAppend(File file, String text) throws IOException {
		text += "\n";
		writeAppend(file, text.getBytes());
	}

	
	/**--STREAM--**/
	public static DataInputStream openInputStream(File file) throws FileNotFoundException {
		return new DataInputStream(new FileInputStream(file));
	}
	
	public static DataOutputStream openOutputStream(File file) throws FileNotFoundException {return openOutputStream(file, false);}
	
	public static DataOutputStream openOutputStream(File file, boolean append) throws FileNotFoundException {
		return new DataOutputStream(new FileOutputStream(file, append));
	}
	
	
	public static String valueOf(char[] data) {return new String(data);}
	
	public static void main(String[] args) {
		Instance.setThreadInstance(Instance.SYSTEM);
		Properties config = new Properties(new File("D:\\Django\\Document 1\\Chat\\Project\\data\\client\\bin\\config.properties"));
		System.out.println(config.getString("lang"));
	}
	
}
