package net.argus.io.download;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.argus.event.download.DownloadEvent;
import net.argus.event.download.EventDownload;
import net.argus.file.FileManager;
import net.argus.instance.CardinalProgram;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

class ProcessDownload {
	
	private URL url;
	private Map<Integer, Thread> downloader = new HashMap<Integer, Thread>();
	
	private EventDownload event;
	
	public ProcessDownload(URL url, EventDownload event) {
		this.url = url;
		this.event = event;
	}
	
	public void getFiles(String[] files, String writeFolder) throws IOException {
		getFiles(files, new String[0], writeFolder);
	}
	
	public void getFiles(String[] files, String[] optional, String writeFolder) throws IOException {
		int i = 0;
		if(files == null || files.length == 0)
			return;
		
		if(optional == null)
			optional = new String[0];
			
		
		for(String file : files) {
			Thread thread = getDownloadThread(file, writeFolder, files.length + optional.length, i);
			downloader.put(i, thread);
			
			i++;
			thread.start();
		}
		
		for(String op : optional) {
			if(FileManager.isExists(writeFolder + "/" + op))
				continue;
			
			Thread thread = getDownloadThread(op, writeFolder, files.length + optional.length, i);
			downloader.put(i, thread);
			
			i++;
			thread.start();
		}
		
		CardinalProgram.wait(this);
	}
	
	private Thread getDownloadThread(String file, String writeFolder, int length, int index) {
		Thread thread = null;
		thread = new Thread(getDownloadRunnable(file, writeFolder, length, index));
		
		thread.setName("Downloader");
		return thread;
	}
	
	public Runnable getDownloadRunnable(String file, String writeFolder, int length, int index) {
		return () -> {
			try {
				byte[] data = Download.get(url, file);
				
				int i = file.lastIndexOf('/');
				String fileAndSuff = file.substring(i!=-1?i+1:0);
				String folder = "";
				if(i != -1)
					folder = file.substring(0, i);
								
				File fileOut = new File(writeFolder + "/" + folder);
								
				if(!fileOut.exists()) {
					fileOut.mkdirs();
					fileOut = new File(fileOut.getAbsolutePath() + "/" + fileAndSuff);
					fileOut.createNewFile();
				}else
					fileOut = new File(fileOut.getAbsolutePath() + "/" + fileAndSuff);
				
				DataOutputStream out = new DataOutputStream(new FileOutputStream(fileOut));
				out.write(data);
				out.close();
				
				event.startEvent(EventDownload.DOWNLOAD, new DownloadEvent(url, file, length, true));
				
				Debug.log("File \"" + file + "\" was download");
			}catch(IOException e){
				Debug.log("File \"" + file + "\" wasn't download: " + e.getMessage(), Info.ERROR);
				
				event.startEvent(EventDownload.DOWNLOAD, new DownloadEvent(url, file, length, false));	
			}
			
			downloader.remove(index);
			verify();
		};
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		Collection<Thread> threads = downloader.values();
		for(Thread thread : threads)
			if(!thread.equals(Thread.currentThread()))
				thread.stop();
			
		downloader.entrySet().clear();
		
	}
	
	private void verify() {
		if(downloader.size() == 0)
			CardinalProgram.notify(this);
	}

}
