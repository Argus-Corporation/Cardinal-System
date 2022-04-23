package net.argus.example;

import java.io.IOException;

import net.argus.instance.Instance;
import net.argus.net.socket.WebStream;
import net.argus.net.web.WebServer;

public class Test {
	
	public static void main(String[] args) throws IOException {
		Instance.setThreadInstance(new Instance("test"));
		WebServer serv = new WebServer(11067);
		
		serv.open();
		
		serv.addWebListener((e) -> {
			try {
				WebStream stream = new WebStream(e.getSocket());
				System.out.println(stream.nextPackage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
				
    }
	
}
