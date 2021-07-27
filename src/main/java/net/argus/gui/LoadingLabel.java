package net.argus.gui;

import net.argus.util.ThreadManager;

public class LoadingLabel extends Label {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055589438047800313L;
	
	private boolean running;
	
	private int speed = 650;

	public LoadingLabel(String name) {
		super(name);
	}
	
	public LoadingLabel(String name, boolean lang) {
		super(name, lang);
	}
	
	public void start() {
		if(!running) {
			running = true;
			getLoadingThread().start();
		}
	}
	
	private Thread getLoadingThread() {
		Thread th = new Thread(() -> {
			int count = 0;
			String original = getText();
			
			while(running) {
				ThreadManager.sleep(speed);
				if(count >= 3) {
					setText(original);
					count = 0;
				}else {
					setText(getText() + ".");
					count++;
				}
			}
			ThreadManager.sleep(speed);
			
			setText(original);
		});
		
		th.setName("label-loading");
		
		return th;
	}
	
	public void stop() {
		running = false;
	}

}
