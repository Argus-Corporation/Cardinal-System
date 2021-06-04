package net.argus.net;

import java.io.IOException;

import net.argus.file.FileSave;
import net.argus.instance.Instance;
import net.argus.net.socket.CardinalSocket;

public class BanRegister {
	
	private FileSave banFile;

	public BanRegister(Instance instance) {
		banFile = new FileSave("ip", "ban", "/", new String[] {"ban", "ip", "num"}, instance);
	}
	
	public void ban(CardinalSocket client) throws IOException {
		ban(client.getInetAddress().getHostAddress());
	}
	
	public void ban(String ip) throws IOException {
		banFile.addValue(ip);
	}
	
	public void unban(String ip) throws IOException {
		banFile.deleteValue(ip);
	}
	
	public boolean isBanned(String ip) {
		try {return banFile.getId(ip) != -1;}
		catch(IOException e) {return false;}
	}

}
