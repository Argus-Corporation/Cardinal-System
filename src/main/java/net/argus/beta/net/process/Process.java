package net.argus.beta.net.process;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Stream;
import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.instance.Instance;

public abstract class Process extends Thread {
	
	public static final Instance PROCESS_INSTANCE = new Instance("process");
	
	private SSLSocket socket;
	private Stream stream;
	
	private ServerProcessRegister register;
		
	public Process(SSLSocket socket, ServerProcessRegister register) throws IOException {
		this.register = register;
		if(socket == null)
			return;
		this.socket = socket;
		
		
		stream = new Stream(socket);
	}
	
	@Override
	public void run() {
		try {process(null);
		}catch(IOException e) {e.printStackTrace();}
	}
	
	protected abstract void process(PackageReturn connectPackage) throws IOException;
	
	public abstract Process create(SSLSocket socket) throws IOException;
	
	protected PackageReturn nextPackage() throws IOException {
		return stream.nextPackage();
	}
	
	public void startThreadProcess() {
		startThread(PROCESS_INSTANCE);
	}
	
	protected void startThread(Instance instance) {
		Instance.startThread(this, instance);
	}
	
	public void startProcess(PackageReturn pack) throws IOException {
		process(pack);
	}
	
	protected void send(Package pack) {
		stream.send(pack);
	}
	
	public void close() throws IOException {
		stream.close();
		socket.close();
	}
	
	public SSLSocket getSocket() {
		return socket;
	}
	
	public ServerProcessRegister getRegister() {
		return register;
	}
	
}
