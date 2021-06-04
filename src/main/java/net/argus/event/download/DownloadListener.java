package net.argus.event.download;

import net.argus.util.Listener;

public interface DownloadListener extends Listener {
	
	public void download(DownloadEvent e);
	
	public void startProcess(DownloadEvent e);
	
	public void endDownload(DownloadEvent e);

}
