package net.argus.proxy;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestAnalizer {
	
	public static final String KEY = "Host: ";
	
	public static URL getURL(String[] header) throws MalformedURLException {
		for(String line : header) {
			int index = line.indexOf(KEY);
			if(index == -1)
				continue;
			
			String urlString = line.substring(index + KEY.length());
			urlString = urlString.substring(0);

			return new URL("http://" + urlString);
		}
		return null;
	}
	
	public static String[] valueOf(String[] header) throws MalformedURLException {
		for(int i = 0; i < header.length; i++)
			if(header[i].startsWith("GET")) {
				URL url = getURL(header);
				header[i] = "GET " + header[i].substring(4 + url.toString().length());
			}
		return header;
	}

}
