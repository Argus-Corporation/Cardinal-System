package net.argus.net.server.command.structure;

import java.util.ArrayList;
import java.util.List;

public class Structure {

	private List<Key> keys = new ArrayList<Key>();
	
	public Structure() {
		keys.add(new Key("command", KeyType.STRING));
	}
	
	public Structure add(String name) {return add(new Key(name, KeyType.STRING));}
	public Structure add(String name, KeyType type) {return add(new Key(name, type));}
	public Structure add(String name, KeyType type, boolean obligatory) {return add(new Key(name, type, obligatory));}
	
	public Structure add(Key key) {keys.add(key); return this;}
	public Structure add(int index, Key key) {
		keys.add(keys.get(keys.size() - 1));
		
		for(int i = keys.size() - 2; i >= index; i--)
			keys.set(i+1, keys.get(i));
		
		keys.set(index, key);
		return this;
	}
	
	public Key getKey(int index) {return keys.get(index);}
	
	public List<Key> getKeys() {return keys;}
	
	public int length() {
		int i = 0;
		for(Key k : keys) if(k.isObligatory()) i++;
		return i;
	}
	
	public int size() {
		return keys.size();
	}
	
	@Override
	public String toString() {
		String str = "/";
		
		for(Key k : keys)
			str += k + " ";
		
		return str;
	}
	
}
