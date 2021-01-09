package net.argus.example.client;

import java.io.IOException;
import java.net.UnknownHostException;

import net.argus.client.Client;
import net.argus.client.ClientManager;
import net.argus.client.ProcessListener;
import net.argus.gui.FrameListener;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class ExampleClient {
	
	public static final int PORT = 11066;
	
	private Client client;
	
	public ExampleClient(String host, String pseudo, String password) throws UnknownHostException, IOException {
		client = new Client(host, PORT);
		
		client.setPseudo(pseudo);
		client.setPassword(password);	
	}
	
	public void logOut() {
		client.sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT).addValue("message", "Leave")));
	}
	
	public void addClientManager(ClientManager manager) {client.addClientManager(manager);}
	public void addProcessListener(ProcessListener listener) {client.addProcessListener(listener);}
	
	public void sendPackage(Package pack) {client.sendPackage(pack);}
	
	public FrameListener getFrameListener() {
		return new FrameListener() {
			public void frameResizing() {}
			@Override
			@SuppressWarnings("deprecation")
			public void frameClosing() {
				if(client != null && client.isConnected()) {
					client.sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT.getId()).addValue("message", "Frame Closing")));
					
					client.getProcessClient().stop();
				}
			}	
			public void frameMinimalized() {}
		};
	}
	

	public void start() throws UnknownHostException, IOException {client.start();}
	
	public Client getClient() {return client;}

}
