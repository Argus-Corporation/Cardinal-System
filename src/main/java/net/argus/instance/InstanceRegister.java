package net.argus.instance;

import java.util.ArrayList;
import java.util.List;

import net.argus.exception.InstanceException;

public class InstanceRegister {
	
	private static List<Instance> instances = new ArrayList<Instance>();
	
	public static void addInstance(Instance instance) {
		if(isValid(instance))
			instances.add(instance);
		else
			throw new InstanceException("Instance \"" + instance.getName() + "\" is already registered");
	}
	
	public static boolean isValid(Instance instance) {
		for(Instance in : instances)
			if(in.getName().equals(instance.getName()))
				return false;
		return true;
	}
	
	public static Instance getInstance(String name) {
		for(Instance in : instances)
			if(in.getName().equals(name))
				return in;
		return null;
	}
	
	public static Instance getMainInstance() {
		if(instances.size() > 0)
			return instances.get(0);
		return null;
	}
	
	public static void remove(Instance instance) {instances.remove(instance);}
	
	public static List<Instance> getInstances() {return instances;}

}
