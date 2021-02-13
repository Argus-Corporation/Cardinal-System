package net.argus.client;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.util.List;

import net.argus.client.info.Info;
import net.argus.client.info.Infos;
import net.argus.exception.SecurityException;
import net.argus.file.cjson.CJSONObject;
import net.argus.util.ErrorCode;
import net.argus.util.ListenerManager;
import net.argus.util.Notification;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class ProcessClient extends Thread {
	
	private ListenerManager<ProcessListener> processManager = new ListenerManager<ProcessListener>();
	private ListenerManager<ClientManager> clientManager = new ListenerManager<ClientManager>();
	
	private Client mainClient;
	private SocketClient client;
	
	public static final int UNCONNECTION = -6;
	public static final int CONNECTION = -5;
	
	public static final int LOG_OUT = -2;
	public static final int MESSAGE = 0;
	public static final int SYSTEM = 1;
	public static final int PSEUDO = 2;
	public static final int ARRAY = 3;
	@Deprecated
	public static final int FILE = 4;
	public static final int NOTIFY = 5;
	
	public ProcessClient(SocketClient client, Client mainClient) {
		this.client = client;
		this.mainClient = mainClient;
		
		new Infos();
	}
	
	@SuppressWarnings("deprecation")
	private void receive() throws SecurityException {
		try {
			
			int type = 0;
			String msg = "";
			String pseudo = "";
			
			Package pack = client.nextPackage();
			
			type = pack.getType();
			
			switch(type) {
				case MESSAGE:
					msg = pack.getValue("message");
					pseudo = pack.getValue("pseudo");
					
					for(ProcessListener manager : processManager.getListeners())	
						if(manager != null) manager.addMessage(new String[] {pseudo, msg});
					break;
					
				case PSEUDO:
					msg = pack.getValue("message");
					client.setPseudo(msg);
					setThreadName(msg);
					
					Debug.log("Pseudo changed to " + client.getPseudo());
					break;
					
				case SYSTEM:
					msg = pack.getValue("message");
					pseudo = pack.getValue("pseudo");
					
					for(ProcessListener manager : processManager.getListeners())	
						if(manager != null) manager.addSystemMessage(new String[] {pseudo, msg});
					break;
					
				case LOG_OUT:
					msg = pack.getValue("message");
					
					ErrorCode code = ErrorCode.valueOf(Integer.valueOf(pack.getValue("code")));
					
					Debug.log("Error code: " + code);
					
					Info info = Info.getInfo(code);
					if(info != null) info.show(msg);
					else Info.show(code);
					
					client.close(msg);
					
					for(ClientManager manager : clientManager.getListeners())	
						if(manager != null) manager.disconnected(msg);
					
					stop();
					break;
					
				case ARRAY:
					CJSONObject valueObj = pack.getObject("value");
					
					switch(Integer.valueOf(valueObj.getValue("type").toString())) {
						case MESSAGE:
							for(CJSONObject obj : valueObj.getArrayValue("array"))
								for(ProcessListener manager : processManager.getListeners())	
									if(manager != null) manager.addSystemMessage(new String[] {null, obj.toString()});
							break;
					}
						
					break;
					
				case NOTIFY:
					Notification.showNotification("Notify", pack.getValue("message"), "ARGUS", MessageType.INFO, "");
					break;
					
			}
			
			for(ClientManager manager : clientManager.getListeners())	
				if(manager != null) manager.receivePackage(pack, this);
		}catch(IOException e) {}
		
	}
	
	public void setThreadName(String name) {setName("CLIENT: " + name.toUpperCase());}
	
	public SocketClient getClient() {return client;}
	
	public List<ProcessListener> getProcessListeners() {return processManager.getListeners();}
	public List<ClientManager> getClientManagers() {return clientManager.getListeners();}
	
	public void addProcessListener(ProcessListener processListener) {this.processManager.addListener(processListener);}
	public void addClientManager(ClientManager clientManager) {this.clientManager.addListener(clientManager);}
	
	public void run() {
		setThreadName(client.getPseudo());
		
		client.sendPackage(new Package(new PackageBuilder(PackageType.PSEUDO).addValue("pseudo", client.getPseudo())));
		client.sendPackage(new Package(new PackageBuilder(PackageType.SYSTEM).addValue("version", Integer.toString(Client.getVersion()))));
		client.sendPackage(new Package(new PackageBuilder(PackageType.PASSWORD).addValue("password", client.getPassword()!=null?client.getPassword():"")));
			
		
		while(mainClient.isRunning()) {
			try {receive();}
			catch(SecurityException e) {e.printStackTrace();}
		}
	}

}
