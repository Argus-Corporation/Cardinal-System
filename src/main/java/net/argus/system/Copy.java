package net.argus.system;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Copy {
	
	public static void copy(String file, String fileCopy) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(new File(file)));
		DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(fileCopy)));
		byte[] data = new byte[in.available()];
		
		in.readFully(data);
		in.close();
		
		out.write(data);
		out.close();
		
	}

}
