package net.argus.net;

import java.io.IOException;

import net.argus.instance.Instance;
import net.argus.net.socket.CardinalSocket;
import net.argus.net.socket.SocketStatus;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public abstract class Connection {
	
	private Process process;
	
	public Connection(Process process) {
		this.process = process;
	}
	
	protected abstract StatusConnection check(CardinalSocket socket) throws IOException;
	
	public void connect(CardinalSocket socket) {
		try {
			StatusConnection status = check(socket);

			if(status.isConnected()) {
				socket.setConnected(true, "connected", SocketStatus.CONNECTED);
				connectionAccepted(socket);
			}else
				connectionRefused(socket, status.getArgument());
		}catch(IOException e) {Debug.log("Error: Connection", Info.ERROR);}
	}
	
	protected void connectionAccepted(CardinalSocket socket) {
		process.setCardinalSocket(socket);
		Instance.startThread(process);
	}
	
	protected void connectionRefused(CardinalSocket socket, String arg) {
		try {socket.close(true, arg);}
		catch(IOException e) {Debug.log("Error when closing the socket", Info.ERROR);}

	}
	
	public Process getProcess() {return process;}
	
}
