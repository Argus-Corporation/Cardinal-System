package net.argus.chat.client.gui.config;

import java.io.IOException;

import javax.swing.BoxLayout;

import net.argus.chat.client.gui.GUIClient;
import net.argus.gui.Label;
import net.argus.gui.Panel;
import net.argus.gui.TextField;

public class PortConfig extends ConfigManager {
	
	private TextField port;
	private TextField portCrypt;

	public PortConfig() {super(1);}

	@Override
	public Panel getConfigPanel() {
		Panel pan = new Panel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		Panel panPort = new Panel();
		Label text = new Label("Port", false);
		
		port = new TextField(10);
		port.setText(GUIClient.config.getString("port"));
		panPort.add(text);
		panPort.add(port);
		
		//-------------------------------------------------\\
		Panel panPortCrypt = new Panel();
		Label textCrypt = new Label("Port crypter", false);

		
		portCrypt = new TextField(10);
		portCrypt.setText(GUIClient.config.getString("port.crypt"));
		panPortCrypt.add(textCrypt);
		panPortCrypt.add(portCrypt);
		
		pan.add(panPort);
		pan.add(panPortCrypt);
		
		return pan;
	}

	@Override
	public int apply() {
		try {
			GUIClient.config.setKey("port", port.getText());
			GUIClient.config.setKey("port.crypt", portCrypt.getText());
		}catch(IOException e) {e.printStackTrace();}
		return 0;
	}
	
}
