package net.argus.event.download;

import net.argus.event.Event;

public class EventDownload extends Event<DownloadListener> {
	
	public static final int DOWNLOAD = 0;
	public static final int START_PROCESS = 1;
	public static final int END_DOWNLOAD = 2;

	@Override
	public void event(DownloadListener listener, int event, Object... objs) {
		switch(event) {
			case DOWNLOAD:
				listener.download((DownloadEvent) objs[0]);
				break;
				
			case START_PROCESS:
				listener.startProcess((DownloadEvent) objs[0]);
				break;
				
			case END_DOWNLOAD:
				listener.endDownload((DownloadEvent) objs[0]);
				break;
		}
	}

}
