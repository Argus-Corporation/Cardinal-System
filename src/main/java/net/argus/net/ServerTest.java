package net.argus.net;

import java.io.IOException;

import net.argus.event.net.process.ProcessEvent;
import net.argus.event.net.process.ProcessListener;
import net.argus.exception.InstanceException;
import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;
import net.argus.net.pack.PackageType;
import net.argus.net.server.Server;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.ServerProcessEvent;
import net.argus.net.socket.CryptoSocket;

@Program(instanceName = "server")
public class ServerTest extends CardinalProgram {

	@Override
	public int main(String[] args) throws InstanceException {
		Server server = new Server(11066);
		server.open(new CryptoSocket(true));
		
		ServerProcessEvent.addProcessListener(new ProcessListener() {
			
			@Override
			public void nextPackage(ProcessEvent e) {
				if(e.getPackage().getType().equals(PackageType.UNDEFINE)) {
					ServerProcess p = (ServerProcess) e.getProcess();
					
					try {
						p.sendToAll(e.getPackage());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		wait(this);
		return 0;
	}

}
