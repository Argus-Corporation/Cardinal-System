package net.argus.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Download {
	
	private URL mainURL;
	
	public Download(URL mainURL) {
		this.mainURL = mainURL;
	}
	
	public int download(String file, File path) {
		try {
			byte[] data = getFile(file);
			write(data, new File(path + "/" + file));
		}catch(Throwable e) {return -1;}
		return 0;
 	}
	
	public byte[] getFile(String file) throws IOException {
		mainURL = new URL(mainURL + file);
		
		byte[] data = new byte[length()];
		DataInputStream dataIn = new DataInputStream(mainURL.openStream());
		dataIn.readFully(data, 0, data.length);
		dataIn.close();
		
		return data;
	}
	
	public void write(byte[] data, File path) throws IOException {
		if(!path.exists())
			new File(path.toString().substring(0, path.toString().lastIndexOf(File.separator))).mkdirs();
		
		
		this.write(data, new FileOutputStream(path));
	}
	
	public void write(byte[] data, OutputStream out) throws IOException {
		DataOutputStream dataOut = new DataOutputStream(out);
		
		dataOut.write(data);
		dataOut.close();
	}
	
	public int length() {
		int length = 0;
		InputStream in;
		try {
			in = mainURL.openStream();
			
			while(in.read() != -1)
				length++;
			
			in.close();
		}catch(IOException e) {}
		
		return length;
	}
	
	public static void main(String[] args) throws MalformedURLException {
		Download down = new Download(new URL("https://argus.alwaysdata.net/"));
		down.download("common/file/client/res/logo.png", new File("D:\\Django\\bureau 1\\"));
		
	}

}
