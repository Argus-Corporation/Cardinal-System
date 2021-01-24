package net.argus.chat.client.gui;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.argus.chat.client.ClientConfig;
import net.argus.chat.client.MainClient;
import net.argus.chat.client.gui.config.PortConfig;
import net.argus.gui.OptionPane;
import net.argus.lang.Lang;
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
		if(ClientConfig.getPort() != -1) {
			String host = fast?getDefaultHost():getHost();
			String pseudo = JOptionPane.showInputDialog(GUIClient.getFrame(), "Pseudo", "Client", JOptionPane.DEFAULT_OPTION);
			String password = JOptionPane.showInputDialog(GUIClient.getFrame(), "Password", "Client", JOptionPane.DEFAULT_OPTION);
			
			try {main.connect(host, pseudo, password);}
			catch(IOException e) {}
		}else {
			int result = OptionPane.showMessageDialog(GUIClient.getFrame(), Lang.getElement("option.porterror.name"), UIManager.getString("Frame.titleErrorText"), JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				GUIClient.configFrame.show();
				GUIClient.configFrame.setSelectedTree(PortConfig.ID);
			}
				
		}
		
	}
	
	public String getDefaultHost() {
		return HostInfo.getProfileConfig().getString("profile0.ip");
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
