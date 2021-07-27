package net.argus.io.ftp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import net.argus.exception.SecurityException;

public class FTPClient {
	
	private String host, user, password;
	
	public FTPClient(String host, String user, String password) throws SecurityException, IOException {
		this.host = host;
		this.user = user;
		this.password = password;
	}
	
	public OutputStream openConnection(URL url) throws IOException {
		return url.openConnection().getOutputStream();
	}
	
	public OutputStream openConnection(URL url, Proxy proxy) throws IOException {
		return url.openConnection(proxy).getOutputStream();
	}
	
	public void transfert(String file, String folder) throws IOException {
		transfert(new File(file), folder);
	}
	
	public void transfert(File file, String folder) throws IOException {
		URL url = getURL(folder + "/" + file.getName());
		
		OutputStream out = openConnection(url);
		out.write(getData(file));
		out.close();
	}
	
	@SuppressWarnings("resource")
	public byte[] getData(File file) throws IOException {
		byte[] data = new byte[(int) file.length()];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		
		in.readFully(data);
		return data;
	}
	
	public String getHost() {return host;}
	public String getUser() {return user;}
	public String getPassword() {return password;}
	
	private URL getURL(String file) throws MalformedURLException {return new URL("ftp://" + user + ":" + password + "@" + host + "/" + file);}
	private URL getURL() throws MalformedURLException {return new URL("ftp://" + user + ":password@" + host + "/");}
	
	@Override
	public String toString() {
		try {return getURL().toString();}
		catch (MalformedURLException e) {e.printStackTrace();}
		return null;
	}

}
