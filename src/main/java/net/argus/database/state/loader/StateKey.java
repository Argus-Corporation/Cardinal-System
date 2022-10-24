package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.state.State;
import net.argus.util.ArrayManager;

public abstract class StateKey {
	
	private static List<StateKey> keys = new ArrayList<StateKey>();
	
	public static final StateKey DATA = new DataStateKey();
	public static final StateKey TABLE = new TableStateKey();
	public static final StateKey MAP = new MapStateKey();
	public static final StateKey COLUMN = new ColumnStateKey();
	
	private static final List<Character> SEPARATOR = ArrayManager.toList(new char[] {':', '@', '}', ','});
	
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
	
	public static StateKey next(List<Character> chars) {
		String name = getNextElement(chars, SEPARATOR);
		
		StateKey key = getStateKey(name);
		return key;	
	}
	
	public static StateKey next(List<Character> chars, char end) {
		String name = getNextElement(chars, end);
		
		StateKey key = getStateKey(name);
		return key;	
	}
	
	public static String getNextElement(List<Character> chars) {
		return getNextElement(chars, SEPARATOR);
	}
	
	public static String getNextElement(List<Character> chars, char end) {
		List<Character> ends = new ArrayList<Character>();
		ends.add(end);
		
		return getNextElement(chars, ends);
	}
	
	public static String getNextElement(List<Character> chars, List<Character> ends) {
		String element = "";
		while(!ends.contains(chars.get(0))) {
			element += chars.get(0);
			chars.remove(0);
		}
		chars.remove(0);
		
		return element;
	}
	
	public static String getNextElementWithLast(List<Character> chars) {
		return getNextElementWithLast(chars, SEPARATOR);
	}
	
	public static String getNextElementWithLast(List<Character> chars, char end) {
		List<Character> ends = new ArrayList<Character>();
		ends.add(end);
		
		return getNextElementWithLast(chars, ends);
	}
	
	public static String getNextElementWithLast(List<Character> chars, List<Character> ends) {
		String element = "";
		while(!ends.contains(chars.get(0))) {
			element += chars.get(0);
			chars.remove(0);
		}
		
		return element;
	}
	
	public abstract State getState(List<Character> chars);
	
	public String getName() {return name;}

}
