package net.argus.client;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.argus.exception.SecurityException;
import net.argus.util.Package;
import net.argus.util.PackageType;
import net.argus.util.debug.Debug;

public class ProcessClient extends Thread {
	
	private ProcessListener proListener;
	
	private ClientManager manager;
	
	private SocketClient client;
	
	public static final int LOG_OUT = -2;
	public static final int MESSAGE = 0;
	public static final int SYSTEM = 1;
	public static final int PSEUDO = 2;
	public static final int ARRAY = 3;
	
	public ProcessClient(SocketClient client) {
		this.client = client;
	}
	
	@SuppressWarnings("deprecation")
	private void receive() throws SecurityException {
		try {
			int type = 0;
			String msg = "";
			String pseudo = "";
			
			Package pack = client.receivePackage();
			
			type = pack.getPackageType().getId();
			
			switch(type) {
				case MESSAGE:
					msg = pack.getMessage();
					
					Package pseudoPack = client.receivePackage();
					
					type = pseudoPack.getPackageType().getId();
					switch(type) {
						case PSEUDO:
							pseudo = pseudoPack.getMessage();
							break;
					}
					if(proListener != null) proListener.addMessage(new String[]{pseudo, msg});
					break;
					
				case PSEUDO:
					client.setPseudo(pack.getMessage());
					Debug.log("Pseudo changed to " + client.getPseudo());
					break;
					
				case SYSTEM:
					msg = pack.getMessage();
					
					Package sysPack = client.receivePackage();
					
					type = sysPack.getPackageType().getId();
					switch(type) {
						case PSEUDO:
							pseudo = sysPack.getMessage();
							break;
					}
					if(proListener != null) proListener.addSystemMessage(new String[] {pseudo, msg});
					break;
					
				case LOG_OUT:
					msg = pack.getMessage();
					
					switch(msg!=null?msg:"") {
						case "version":
							JOptionPane.showMessageDialog(null, "Obsolete version " + 0x12345, "Alert Server", JOptionPane.ERROR_MESSAGE);
							break;
						case "corrupt version":
							JOptionPane.showMessageDialog(null, "Corrupt version " + 0x45973, "Alert Server", JOptionPane.ERROR_MESSAGE);
							break;
						case "close":
							JOptionPane.showMessageDialog(null, "Serveur close " + 0x79895, "Alert Server", JOptionPane.ERROR_MESSAGE);
							break;
					}
					client.close(msg);
					
					if(manager != null) manager.disconnected(msg);
					
					stop();
			}
			if(manager != null) manager.receiveMessage(type);
		}catch(IOException e) {}
		
	}
	
	public void addProcessListener(ProcessListener processListener) {this.proListener = processListener;}
	public void addClientManager(ClientManager clientManager) {this.manager = clientManager;}
	
	@SuppressWarnings("deprecation")
	public void run() {
		currentThread().setName("CLIENT: " + client.getPseudo().toUpperCase());
		try {
			client.init();
			
			client.sendPackage(new Package(PackageType.PSEUDO, client.getPseudo()));
			client.sendPackage(new Package(PackageType.SYSTEM, Integer.toString(Client.getVersion())));
			client.sendPackage(new Package(PackageType.PASSWORD, client.getPassword()!=null?client.getPassword():""));
			
			if(client.isUseKey() != client.isServerUseKey()) {
				client.sendPackage(new Package(PackageType.LOG_OUT, "Client and server is not compatible"));
				client.close("Client and server is not compatible");
				stop();
			}
		}catch(IOException | SecurityException e) {e.printStackTrace();}
		while(Client.isRunning()) {
			try {receive();}
			catch(SecurityException e) {e.printStackTrace();}
		}
	}


}
