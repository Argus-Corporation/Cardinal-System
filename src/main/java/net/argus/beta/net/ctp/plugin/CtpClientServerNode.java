package net.argus.beta.net.ctp.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.beta.net.process.ProcessRegister;
import net.argus.util.Version;

public class CtpClientServerNode <R extends ProcessRegister, P extends CtpPlugin<R>> {
	
	private Version version;
	
	private List<P> plugins = new ArrayList<P>();
	
	private R processRegister;
	
	public CtpClientServerNode(R processRegister, Version version) {
		this.processRegister = processRegister;
		this.version = version;
	}
	
	public void addPlugin(P plugin) throws IOException {
		if(!plugins.contains(plugin)) {
			plugin.setDefault(processRegister);
			plugins.add(plugin);
		}
	}
	
	public List<P> getPlugins() {return plugins;}
	
	protected R getRegister() {return processRegister;}

	public Version getVersion() {
		return version;
	}
}
