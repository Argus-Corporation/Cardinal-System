package net.argus.io.download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.event.download.DownloadEvent;
import net.argus.event.download.DownloadListener;
import net.argus.event.download.EventDownload;
import net.argus.exception.CJSONException;
import net.argus.file.CJSONFile;
import net.argus.system.OS;
import net.argus.util.debug.Debug;

public class Downloader {
	
	private URL url;
	
	private String[] commonFiles;
	private String[] nativeFiles;
	
	private ProcessDownload d;
	
	private EventDownload event = new EventDownload();
	
	public Downloader(CJSON cjson, OS os) throws MalformedURLException {
		init(cjson, os);
	}
	
	public Downloader(CJSON cjson) throws MalformedURLException {
		this(cjson, OS.currentOS());
	}
	
	public Downloader(CJSONFile cjson) throws MalformedURLException {
		this(CJSONParser.getCJSON(cjson), OS.currentOS());
	}
	
	private void init(CJSON cjson, OS os) throws MalformedURLException {
		try {
			url = new URL(cjson.getString("download.url"));
			
			commonFiles = cjson.getStringArray("download.files.common");
			nativeFiles = cjson.getStringArray("download.files." + os.toString().toLowerCase());
		}catch(CJSONException e) {throw new Error(e);}
	}
	
	public void getAll(String writeFolder) throws IOException {
		d = new ProcessDownload(url, event);
		
		event.startEvent(EventDownload.START_PROCESS, new DownloadEvent(url, "common", commonFiles.length, true));
		getCommons(d, writeFolder);
		
		event.startEvent(EventDownload.START_PROCESS, new DownloadEvent(url, "native", nativeFiles.length, true));
		getNatives(d, writeFolder);
		
		event.startEvent(EventDownload.END_DOWNLOAD, new DownloadEvent(url, "all", -1, true));
	}
	
	public void getCommons(ProcessDownload d, String writeFolder) throws IOException {d.getFiles(commonFiles, writeFolder);}
	
	public void getNatives(ProcessDownload d, String writeFolder) throws IOException {d.getFiles(nativeFiles, writeFolder);}
	
	public URL getURL() {return url;}
	
	public String[] getCommonFiles() {return commonFiles;}
	public String[] getNativeFiles() {return nativeFiles;}
	
	public void addDownloadListener(DownloadListener listener) {event.addListener(listener);}
	
	public void stop() {
		if(d != null)
			d.stop();
		
		Debug.log("The downlod wasn't started");
	}
	
}
