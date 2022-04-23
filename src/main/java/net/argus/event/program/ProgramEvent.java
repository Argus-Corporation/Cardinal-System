package net.argus.event.program;

public class ProgramEvent {
	
	private String message;
	
	public ProgramEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {return message;}

}
