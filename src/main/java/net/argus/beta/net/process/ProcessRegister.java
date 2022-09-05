package net.argus.beta.net.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocket;

public class ProcessRegister {
	
	private List<ProcessHandler> handlers = new ArrayList<ProcessHandler>();
	
	public ProcessRegister() {}
	
	protected void addProcessHandler(ProcessHandler handler) {
		if(!handlers.contains(handler))
			handlers.add(handler);
	}

	public void linkPathToProcess(String path, Process process) {
		addProcessHandler(new ProcessHandler(path, process));
	}
	
	public  Process getProcess(String path, SSLSocket socket) throws IOException {
		for(ProcessHandler h : handlers)
			if(path.equals(h.getPath()))
				return h.getProcess().create(socket);
		
		return null;
	}
	
	@Override
	public String toString() {
		return handlers.toString();
	}

}
