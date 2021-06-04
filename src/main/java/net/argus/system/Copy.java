package net.argus.system;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Copy {
	
	public static void copy(InputStream in, OutputStream out) throws IOException {
		copy(new DataInputStream(in), new DataOutputStream(out));	
	}
	
	public static void copy(String fileIn, String fileOut) throws IOException {
		copy(new FileInputStream(new File(fileIn)), new FileOutputStream(new File(fileOut)));	
	}
	
	public static void copy(DataInputStream in, DataOutputStream out) throws IOException {
		byte[] data = new byte[in.available()];
		
		in.readFully(data);
		in.close();
		
		out.write(data);
		out.close();
	}

}
