package net.argus.event.download;

import java.net.URL;

public class DownloadEvent {
	
	private URL url;
	private String file;
	private int length;
	private boolean complete;
	
	public DownloadEvent(URL url, String file, int length, boolean complete) {
		this.url = url;
		this.file = file;
		this.length = length;
		this.complete = complete;
	}
	
	public URL getURL() {return url;}
	public String getFile() {return file;}
	public int getLength() {return length;}
	public boolean isComplete() {return complete;}

}
