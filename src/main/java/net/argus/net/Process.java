package net.argus.net;

import java.io.IOException;

import net.argus.event.net.process.EventProcess;
import net.argus.event.net.process.ProcessEvent;
import net.argus.event.net.process.ProcessListener;
import net.argus.net.pack.Package;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public abstract class Process extends Thread {
	
	protected CardinalSocket socket;
	
	protected EventProcess event = new EventProcess();
	
	protected boolean running = true;
	
	public Process() {}
	
	public Process(CardinalSocket socket) {this.socket = socket;}
	
	public abstract void newPackage(Package pack) throws IOException;
	
	public boolean isError() {
		return running;
	}
	
	private void loop() throws IOException {
		while(running) {
			Package pack = socket.nextPackage();
			
			if(pack != null && !pack.isNull()) {
				newPackage(pack);
				
				event.startEvent(EventProcess.NEXT_PACKAGE, new ProcessEvent(this, pack));
			}
		}
	}
	
	public CardinalSocket getSocket() {return socket;}
	
	@Override
	public synchronized void start() {
		if(socket != null)
			super.start();
	}
	
	@Override
	public void run() {
		currentThread().setName("Process: " + socket.getProfile().getName());
		try {loop();}
		catch(IOException e) {
			if(isError() && !socket.isClose()) Debug.log("The connection was closed", Info.ERROR);
		}
		
		/**--CLOSE--**/
		logOut("process");
	}
	
	public void logOut(String arg) {
		try {socket.close(arg);}
		catch(IOException e) {e.printStackTrace();}
	}
	
	public void addProcessListener(ProcessListener listener) {event.addListener(listener);}

	public void setCardinalSocket(CardinalSocket socket) {this.socket = socket;}
	public CardinalSocket getCardinalSocket() {return socket;}
	
	public void close() {running = false;}
	
}
