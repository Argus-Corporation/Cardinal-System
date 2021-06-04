package net.argus.io.download;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Download {
	
	public static byte[][] get(URL url, String[] files) throws IOException {
		byte[][] data = new byte[files.length][];
		int i = 0;
		for(String file : files)
			data[i++] = get(url, file);

		return data;
	}
	
	public static byte[] get(URL url, String file) throws IOException {
		return get(new URL(url + "/" + file));
	}
	
	public static byte[] get(URL url) throws IOException {
		return get(openStream(url));
	}
	
	public static byte[] get(URLConnection connection) throws IOException {		
		DataInputStream in = new DataInputStream(connection.getInputStream());
		byte[] data = new byte[length(connection.getURL())];
		
		in.readFully(data);
		return data;
	}
	
	public static URLConnection openStream(URL url) throws IOException {
		return url.openConnection();
	}
	
	public static int length(URL url) {
	    URLConnection conn = null;
	    try {
	        conn = url.openConnection();
	        if(conn instanceof HttpURLConnection)
	            ((HttpURLConnection)conn).setRequestMethod("HEAD");
	        else if(conn instanceof HttpsURLConnection)
	        	((HttpsURLConnection)conn).setRequestMethod("HEAD");
	        
	        conn.getInputStream();
	        return conn.getContentLength();
	    }catch(IOException e) {throw new RuntimeException(e);}
	    finally {
	        if(conn instanceof HttpURLConnection)
	            ((HttpURLConnection)conn).disconnect();
	        else if(conn instanceof HttpsURLConnection)
	        	((HttpsURLConnection)conn).disconnect();

	    }
	}

}
