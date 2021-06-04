package net.argus.util.notify.center;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.notify.NotifyInfo;

public class NotifyCenter {
	
	private List<NotifyInfo> infos = new ArrayList<NotifyInfo>();
	
	public void addNotify(NotifyInfo info) {infos.add(info);}
	
	public void remove(int index) {infos.remove(index);}
	public void remove(NotifyInfo info) {infos.remove(info);}
	
	public List<NotifyInfo> getNotifys() {return infos;}

}
