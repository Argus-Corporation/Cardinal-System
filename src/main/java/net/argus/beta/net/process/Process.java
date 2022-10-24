package net.argus.beta.net.process;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Stream;
import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.instance.Instance;
import net.argus.util.ThreadLocal;

public abstract class Process extends Thread {
	
	public static final Instance PROCESS_INSTANCE = new Instance("process");
	
	private ThreadLocal<PackageReturn> packs = new ThreadLocal<PackageReturn>();
		
	private SSLSocket socket;
	private Stream stream;
	
	private ProcessRegister register;
	
	public Process(SSLSocket socket, ProcessRegister register) throws IOException {
		this.register = register;
		if(socket == null)
			return;
		this.socket = socket;
				
		stream = new Stream(socket);
	}
	
	@Override
	public void run() {
		try {startProcess(packs.get());}
		catch(IOException e) {e.printStackTrace();}
	}
	
	protected abstract ProcessReturn process(PackageReturn connectPackage) throws IOException;
	
	public abstract Process create(SSLSocket socket) throws IOException;
	
	protected PackageReturn nextPackage() throws IOException {
		return stream.nextPackage();
	}
	
	public void startThreadProcess(PackageReturn connection) {
		startThread(PROCESS_INSTANCE, connection);
	}
	
	protected void startThread(Instance instance, PackageReturn connection) {
		packs.set(this, connection);
		Instance.startThread(this, instance);
	}
	
	public ProcessReturn startProcess(PackageReturn pack) throws IOException {
		ProcessReturn ret = process(pack);
		if(!ret.isSuccess() && pack != null)
			send(PackagePrefab.getErrorPackage(pack.getString("path"), ret.getMessage()));
		return ret;
	}
	
	protected void send(Package pack) {
		stream.send(pack);
	}
	
	public void close() throws IOException {
		if(stream != null)
			stream.close();
		if(socket != null)
			socket.close();
	}
	
	public Stream getStream() {
		return stream;
	}
	
	public SSLSocket getSocket() {
		return socket;
	}
	
	public ProcessRegister getRegister() {
		return register;
	}
	
}
