package net.argus.client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import net.argus.client.info.Info;
import net.argus.client.info.Infos;
import net.argus.exception.SecurityException;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class ProcessClient extends Thread {
	
	private ProcessListener proListener;
	
	private ClientManager manager;
	
	private Client mainClient;
	private SocketClient client;
	
	public static final int LOG_OUT = -2;
	public static final int MESSAGE = 0;
	public static final int SYSTEM = 1;
	public static final int PSEUDO = 2;
	public static final int ARRAY = 3;
	public static final int FILE = 4;
	
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
					
					if(proListener != null) proListener.addMessage(new String[] {pseudo, msg});
					break;
					
				case PSEUDO:
					msg = pack.getValue("pseudo");
					client.setPseudo(msg);
					Debug.log("Pseudo changed to " + client.getPseudo());
					break;
					
				case SYSTEM:
					msg = pack.getValue("message");
					pseudo = pack.getValue("pseudo");
					
					if(proListener != null) proListener.addSystemMessage(new String[] {pseudo, msg});
					break;
					
				case LOG_OUT:
					msg = pack.getValue("message");
					/*switch(msg != null ? msg : "") {
						case "version":
							JOptionPane.showMessageDialog(null, "Obsolete version " + 0x12345, "Alert Server", JOptionPane.ERROR_MESSAGE);
							break;
						case "corrupt version":
							JOptionPane.showMessageDialog(null, "Corrupt version " + 0x45973, "Alert Server", JOptionPane.ERROR_MESSAGE);
							break;
						case "close":
							JOptionPane.showMessageDialog(null, "Serveur close " + 0x79895, "Alert Server", JOptionPane.ERROR_MESSAGE);
							break;
					}*/
					
					Info info = Info.getInfo(msg);
					if(info != null) info.run();
					
					client.close(msg);
					
					if(manager != null) manager.disconnected(msg);
					
					stop();
					break;
					
				case FILE:
					String fileName = pack.getObject("value").getValue("fileName").toString();
					String extention = pack.getObject("value").getValue("extention").toString();
					byte[] data = pack.getObject("value").getByte("data");
					
					String path = "";
					
					JFileChooser choos = new JFileChooser(System.getProperty("user.home"));
					choos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					int returnValue = -1;
					while(returnValue != JFileChooser.APPROVE_OPTION)
						returnValue = choos.showOpenDialog(null);
					
					path = choos.getSelectedFile().getPath();
					
					DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(path + "\\" + fileName + "." + extention)));
					out.write(data);
					out.close();
					
					Debug.log("File created: " + path);
					
					break;
			}
			if(manager != null) manager.receivePackage(pack, this);
		}catch(IOException e) {}
		
	}
	
	public ProcessListener getProcessListener() {return proListener;}
	public ClientManager getClientManager() {return manager;}
	public SocketClient getClient() {return client;}
	
	public void addProcessListener(ProcessListener processListener) {this.proListener = processListener;}
	public void addClientManager(ClientManager clientManager) {this.manager = clientManager;}
	
	@SuppressWarnings("deprecation")
	public void run() {
		currentThread().setName("CLIENT: " + client.getPseudo().toUpperCase());
		try {
			client.init();
			
			client.sendPackage(new Package(new PackageBuilder(PSEUDO).addValue("pseudo", client.getPseudo())));
			client.sendPackage(new Package(new PackageBuilder(SYSTEM).addValue("version", Integer.toString(Client.getVersion()))));
			client.sendPackage(new Package(new PackageBuilder(PackageType.PASSWORD.getId()).addValue("password", client.getPassword()!=null?client.getPassword():"")));
			
			if(client.isUseKey() != client.isServerUseKey()) {
				client.sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT.getId()).addValue("message", "Client and server is not compatible")));
				client.close("Client and server is not compatible");
				stop();
			}
		}catch(IOException | SecurityException e) {e.printStackTrace();}
		
		while(mainClient.isRunning()) {
			try {receive();}
			catch(SecurityException e) {e.printStackTrace();}
		}
	}

}
