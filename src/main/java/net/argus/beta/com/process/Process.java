package net.argus.beta.com.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.beta.com.CardinalSocket;
import net.argus.beta.com.NetSocket;
import net.argus.beta.com.pack.Package;
import net.argus.event.com.process.EventProcess;
import net.argus.event.com.process.ProcessEvent;
import net.argus.event.com.process.ProcessListener;
import net.argus.instance.Instance;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public abstract class Process {
	
	private NetSocket socket;
	
	private boolean running = true;
	private EventProcess event = new EventProcess();
	
	private List<Package> queue = new ArrayList<Package>();
	
	public Process(NetSocket socket) {
		this.socket = socket;
	}
	
	public void start(Object ... objs) {
		Instance.startThread(new Thread(() -> {
			try {
				ConnectionStatus status = init(objs);
				if(!status.isAccepted()) {
					event.startEvent(EventProcess.REFUSE, new ProcessEvent(this, null, status.getArgument()));
					return;
				}
				
				event.startEvent(EventProcess.ACCEPT, new ProcessEvent(this, null, status.getArgument()));
				
				releaseQueue();
				while(!socket.isClosed() && running) {
					Package pack = socket.nextPackage();
					nextPackage0(pack);
				}
				
			}catch(IOException e) {
				if(isError() && !socket.isClosed()) 
					Debug.log("Error in Process", Info.ERROR);
			}
		}));
		Debug.log("Process started");
	}
	
	private boolean isError() {
		return running;
	}
	
	protected abstract ConnectionStatus init(Object ... objs) throws IOException;
	
	protected abstract void nextPackage(Package pack) throws IOException;
	
	private void nextPackage0(Package pack) throws IOException {
		nextPackage(pack);
		event.startEvent(EventProcess.NEW_PACKAGE, new ProcessEvent(this,  pack, null));
	}
	
	protected void addQueue(Package pack) {
		queue.add(pack);
	}
	
	protected void releaseQueue() throws IOException {
		for(Package pack : queue)
			nextPackage0(pack);
		
		queue.clear();
	}
	
	public void close(String arg) throws IOException {
		running = false;
		socket.close();
		event.startEvent(EventProcess.CLOSE, new ProcessEvent(this, null, arg));
	}
	
	public void send(Package pack) throws IOException {
		socket.send(pack);
	}

	public CardinalSocket getSocket() {return socket;}
	
	public void addProcessListener(ProcessListener listener) {event.addListener(listener);}
	
}
