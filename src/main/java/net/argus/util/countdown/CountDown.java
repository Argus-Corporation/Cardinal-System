package net.argus.util.countdown;

import net.argus.gui.Label;
import net.argus.util.ListenerManager;
import net.argus.util.ThreadManager;

public class CountDown {
	
	private ListenerManager<CountDownListener> countManager = new ListenerManager<CountDownListener>();
	
	private int max;
	
	private Label lab;
	private String prefix;
	
	public CountDown(int max) {
		this.max = max;
		lab = new Label("", false);
	}
	
	public Label getLabelCountDown(String prefix) {
		this.prefix = prefix;
		return lab;
	}
	
	public void start() {
		new ThreadManager("count down").start(getRunnableCountDown());
	}
	
	public void addCountDownListener(CountDownListener listener) {countManager.addListener(listener);}
	
	private Runnable getRunnableCountDown() {
		return () -> {
			for(int i = max; i >= 0; i--) {
				lab.setText(prefix + " " + Integer.toString(i));
				
				for(CountDownListener lis : countManager)
					lis.valueChange(new CountDownEvent(i));
				
				ThreadManager.sleep(1000);
			}
			
			for(CountDownListener lis : countManager)
				lis.finish(new CountDownEvent(0));
		};
	}

}
