package net.argus.example.client.gui;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.argus.example.client.MainClient;
import net.argus.util.ThreadManager;

public class Connect extends Thread {
	
	private MainClient main;
	private boolean fast;
	
	public Connect(MainClient main, boolean fast) {
		this.main = main;
		this.fast = fast;
	}
	
	@Override
	public void run() {
		String host = fast?getDefaultHost():getHost();
		String pseudo = JOptionPane.showInputDialog(GUIClient.getFrame(), "Pseudo", "Client", JOptionPane.DEFAULT_OPTION);
		String password = JOptionPane.showInputDialog(GUIClient.getFrame(), "Password", "Client", JOptionPane.DEFAULT_OPTION);
		
		try {main.connect(host, pseudo, password);}
		catch(IOException e) {}
		
	}
	
	public String getDefaultHost() {
		return HostInfo.getIpConfig().getString("ip0");
	}
	
	public String getHost() {
		HostInfo.openInfoDialog(GUIClient.getFrame());
		
		String host = null;
		while(host == null) {
			host = HostInfo.getHost();
			ThreadManager.sleep(1);
		}
		
		return host;
	}

}
