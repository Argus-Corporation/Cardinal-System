package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

public abstract class StateKey {
	
	private static List<StateKey> keys = new ArrayList<StateKey>();
	
	public static final StateKey DATA = new DataStateKey();
	
	private String name;
	
	public StateKey(String name) {
		this.name = name;
		keys.add(this);
	}
	
	public static StateKey getStateKey(String name) {
		for(StateKey key : keys)
			if(key.name.toUpperCase().equals(name.toUpperCase()))
				return key;
		return null;
	}
	
	public static StateKey next(String saved) {
		String name = saved.substring(0, saved.indexOf(':'));
		saved.substring(name.length());
		
		StateKey key = getStateKey(name);
		return key;	
	}
	
	public abstract void getState(String saved);

}
