package net.argus.instance;

import net.argus.exception.InstanceException;
import net.argus.file.FileManager;
import net.argus.util.ThreadLocal;

public class Instance {
	
	public static final Instance SYSTEM = new Instance("system").setRootPath("");
	
	private static ThreadLocal<Instance> inst = new ThreadLocal<Instance>();
	
	private String name;
	
	private String rootPath;
	
	public Instance(String name) {
		this(name, "data");
	}
	
	public Instance(String name, String rootFolder) {
		this.name = name;
		if(rootFolder == null || rootFolder.equals(""))
			rootFolder = "data";
		
		setRootPath(rootFolder + "/" + name);
		
		InstanceRegister.addInstance(this);
	}
	
	/**
	 * Start thread
	 * @param thread
	 * @param instance
	 */
	public static void startThread(Thread thread) {startThread(thread, currentInstance());}
	
	/**
	 * Start thread width instance
	 * @param thread
	 * @param instance
	 */
	public static void startThread(Thread thread, Instance instance) {
		if(instance == null)
			throw InstanceException.getInstanceNull();
		
		if(instance.equals(SYSTEM))
			throw InstanceException.getStartThreadWithSystemInstance();
		
		inst.set(thread, instance);
		thread.start();
	}
	
	public String getName() {return name;}
	public String getRootPath() {return rootPath;}
	
	private Instance setRootPath(String rootFolder) {
		this.rootPath = FileManager.getMainPath() + "/" + rootFolder + "/";
		return this;
	}
	
	public Instance setRootFolder(String rootFolder) {
		return setRootPath(rootFolder + "/" + name);
	}
	
	public static Instance currentInstance() {return inst.get();}
	
	public static void setThreadInstance(Instance instance) {inst.set(instance);}
	
	@Override
	public String toString() {
		return "Instance@" + name;
	}

}
