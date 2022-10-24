package net.argus.beta.net.process;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;
import net.argus.instance.Instance;

public abstract class SwitchProcess extends Process {
	
	public static final Instance SWITCH_INSTANCE = new Instance("switch-process");

	public SwitchProcess(SSLSocket socket, ProcessRegister register) throws IOException {
		super(socket, register);
	}

	@Override
	protected ProcessReturn process(PackageReturn connectPackage) throws IOException {
		PackageReturn ret = connectPackage==null?nextPackage():connectPackage;
		
		if(ret == null)
			return new ProcessReturn(false, "package null");
		
		CJSONValue path = null;
		if((path = ret.getValue("path")) == null || !(path instanceof CJSONString))
			return new ProcessReturn(false, "path is not defined");  //send close package and close

		Process process = getRegister().getProcess(ret.getString("path"), getSocket());
		if(process == null)
			return new ProcessReturn(false, "server don't find process \"" + path.getValue() + "\"");  //send close package and close

		return process.create(getSocket()).startProcess(ret);
	}
	
	@Override
	public void startThreadProcess(PackageReturn connection) {
		startThread(SWITCH_INSTANCE, connection);
	}

}
