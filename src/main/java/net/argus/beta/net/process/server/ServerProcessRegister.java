package net.argus.beta.net.process.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.Process;

public class ServerProcessRegister {
	
	private List<ServerProcessHandler> handlers = new ArrayList<ServerProcessHandler>();
	
	protected void addServerProcessHandler(ServerProcessHandler handler) {
		if(!handlers.contains(handler))
			handlers.add(handler);
	}

	public void linkPathToProcess(String path, Process process) {
		addServerProcessHandler(new ServerProcessHandler(path, process));
	}
	
	public void linkPathToProcess(ServerProcess process) {
		addServerProcessHandler(new ServerProcessHandler(process.getPath(), process));
	}
	
	public  Process getProcess(String path, SSLSocket socket) throws IOException {
		for(ServerProcessHandler h : handlers)
			if(path.equals(h.getPath()))
				return h.getProcess().create(socket);
		
		return null;
	}
	
	@Override
	public String toString() {
		return handlers.toString();
	}
	
}
