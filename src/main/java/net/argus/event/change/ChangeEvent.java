package net.argus.event.change;

public class ChangeEvent {
	
	private String old;
	private String newVal;
	
	public ChangeEvent(String old, String newVal) {
		this.old = old;
		this.newVal = newVal;
	}
	
	public String getOld() {return old;}
	public String getNew() {return newVal;}

}
