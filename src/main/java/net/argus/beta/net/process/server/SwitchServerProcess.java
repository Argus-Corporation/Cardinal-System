package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.Process;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;
import net.argus.instance.Instance;

public class SwitchServerProcess extends Process {
	
	public static final Instance SWITCH_INSTANCE = new Instance("switch-process");

	public SwitchServerProcess(SSLSocket socket, ServerProcessRegister register) throws IOException {
		super(socket, register);
	}

	@Override
	protected void process(PackageReturn connectPack) throws IOException {
		PackageReturn ret = nextPackage();
			
		CJSONValue path = null;
		if((path = ret.getValue("path")) == null || !(path instanceof CJSONString))
			return;  //send close package and close

		Process process = getRegister().getProcess(ret.getString("path"), getSocket());
		if(process == null)
			return;  //send close package and close

		process.create(getSocket()).startProcess(ret);
	}
	
	@Override
	public void startThreadProcess() {
		startThread(SWITCH_INSTANCE);
	}

	@Override
	public Process create(SSLSocket socket) throws IOException {
		return new SwitchServerProcess(socket, getRegister());
	}
	/*
	static {
		try {
			ServerProcessHandler.linkPathToProcess(new CtpServerRequestProcess(null));
			ServerProcessHandler.linkPathToProcess(new CqlServerQueryProcess(null));
		}catch(IOException e) {e.printStackTrace();}
	
	}*/

}
