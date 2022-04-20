package net.argus.util.notify;

import net.argus.event.notify.EventNotify;
import net.argus.event.notify.NotifyListener;
import net.argus.util.Display;
import net.argus.util.notify.center.NotifyCenter;

public class Notify {
	
	private NotifyCenter center = new NotifyCenter();
	
	private NotifyWindow window;
	private NotifyComponent component;
	
	private EventNotify event = new EventNotify();
	
	private NotifyWait wait = new NotifyWait(this);
	
	public Notify(NotifyComponent component) {
		this.component = component;
	}
	
	public void show(String title, String message) {
		NotifyInfo info = new NotifyInfo();
		
		info.setTitle(title);
		info.setIcon("D:\\Django\\Document 1\\Chat\\Project\\res\\favicon16x16.png");
		info.setMessage(message);
		
		show(info);
	}
	
	public void show(NotifyInfo info) {
		if(!window.isVisible()) {
			center.addNotify(info);
			component.setInfo(info);
			show();
		}else
			wait.addNotify(info);
	}
	
	private void show() {
		window.setNotifyComponent(component);
		component.show();
		window.pack();
		window.setVisible(true);
	}
	
	public NotifyWindow getWindow() {return window;}
	public NotifyComponent getComponent() {return component;}
	
	public NotifyCenter getCenter() {return center;}
	
	public void addNotifyListener(NotifyListener listener) {event.addListener(listener);}
	protected EventNotify getEvent() {return event;}
	
	public static void main(String[] args) {
	/*	UserSystem.notify.show("Test", "Hello World!");
		UserSystem.notify.show("Test", ":ldwrfgn:krdnjgkjfdngkdfhngkidfhngifdhfxdgfdx jknfgdxjfdb jhdfb gjxfdbgkjbngkidhfj!gndkguuirdkjhkghjkjhgkhjsfdugdf");
		UserSystem.notify.show("Test", "eqfpdgklfdnjgifdjgndfih.");
		*/
	}
	
	{
		if(!Display.isHeadlessInstance()) {
			window = new NotifyWindow(this);
		}
	}
	
}
