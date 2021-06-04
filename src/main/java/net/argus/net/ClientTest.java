package net.argus.net;

import java.io.IOException;
import java.util.Scanner;

import net.argus.event.net.socket.SocketEvent;
import net.argus.event.net.socket.SocketListener;
import net.argus.exception.InstanceException;
import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;
import net.argus.net.client.Client;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackageBuilder;
import net.argus.net.pack.PackageType;
import net.argus.net.socket.CryptoSocket;

@Program(instanceName = "client")
public class ClientTest extends CardinalProgram {

	@Override
	public int main(String[] args) throws InstanceException {
		Client client = new Client("0.0.0.0", 11066, new CryptoSocket(false));
		
		ClientTest thisClient = this;
		
		client.addSocketListener(new SocketListener() {
			public void connectionRefused(SocketEvent e) { System.out.println("refused");}
			public void disconnect(SocketEvent e) {        System.out.println("disconected"); CardinalProgram.notify(thisClient);}
			public void connect(SocketEvent e) {           System.out.println("connected");}
		});
		
		client.addProcessListener((e) -> {
			System.out.println(e.getPackage());
		});
		try {
			client.connect("rt");
			
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			while(true) {
				client.send(new Package(new PackageBuilder(PackageType.UNDEFINE).addKey("Message", sc.nextLine())));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		wait(this);
		return 0;
	}
	
	

}
