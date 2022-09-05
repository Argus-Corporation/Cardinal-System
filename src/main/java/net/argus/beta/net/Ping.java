package net.argus.beta.net;

import net.argus.beta.net.pack.PackageReturn;

public class Ping {
	
	private int ping;
	private boolean more;
	
	public Ping(int ping) {
		if(ping > 999) {
			ping = 999;
			more = true;
		}else if(ping < 0)
			ping = -1;
		
		this.ping = ping;
	}
	
	public Ping(PackageReturn ret) {
		if(ret == null)
			throw new NullPointerException();
		
		if(!ret.getString("path").equals("/pong"))
			throw new IllegalArgumentException();
		
		if(ret.getValue("ping") == null)
			throw new IllegalArgumentException();

		int ping = ret.getInteger("ping");
		
		if(ping > 999) {
			this.ping = 999;
			more = true;
		}else if(ping < 0)
			this.ping = -1;
		else
			this.ping = ping;
	}
	
	public int getPing() {
		return ping;
	}
	
	public boolean isMore() {
		return more;
	}
	
	@Override
	public String toString() {
		return toStringPing(ping, more);
	}
	
	public static String toStringPing(int ping) {
		return toStringPing(ping, ping>999);
	}
	
	private static String toStringPing(int ping, boolean more) {
		return more?("+"+ping):(ping+"") + "ms";
	}

}
