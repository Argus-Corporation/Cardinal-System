package net.argus.util.notify;

import java.util.ArrayList;
import java.util.List;

import net.argus.event.notify.NotifyEvent;
import net.argus.event.notify.NotifyListener;

public class NotifyWait {
	
	private List<NotifyInfo> infos = new ArrayList<NotifyInfo>();
	
	public NotifyWait(Notify notify) {	
		notify.addNotifyListener(new NotifyListener() {
			public void show(NotifyEvent e) {}
			public void hide(NotifyEvent e) {
				//System.out.println(e.getSource());
				if(infos.size() > 0) {
					notify.show(infos.get(0));
					infos.remove(0);
				}
			}
			
		});
	}
	
	public void addNotify(NotifyInfo info) {infos.add(info);}
	public List<NotifyInfo> getNotifys() {return infos;}

}
