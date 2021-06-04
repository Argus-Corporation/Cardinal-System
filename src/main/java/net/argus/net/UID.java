package net.argus.net;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.Math;

public class UID {
	
	private static List<UID> uids = new ArrayList<UID>();
	
	private int uid;
	
	public UID() {
		uid = genUID();
		uids.add(this);
	}
	
	public int getUID() {return uid;}
	
	private int genUID() {
		int id = 0;
		do {
			id = Math.random(0, 999999);
		}while(isUsed(id));
		
		return id;
	}
	
	public static boolean isUsed(int uid) {
		for(UID i : uids)
			if(i.uid == uid)
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "UID@" + uid;
	}

}
