package net.argus.exception;

public class InstanceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -103906584178541823L;
	
	public InstanceException() {}
	
	public InstanceException(String text) {
		super(text);
	}
	
	public static InstanceException getInstanceNull() {return new InstanceException("instance null");}
	public static InstanceException getStartThreadWithSystemInstance() {return new InstanceException("start thread with system instance");}

}
