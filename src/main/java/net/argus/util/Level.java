package net.argus.util;

public enum Level {
	
	LOW(-1), MEDIUM(0), HIGH(1);
	
	int priority;
	
	Level(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {return priority;}

}
