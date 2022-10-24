package net.argus.beta.net.process;

public class ProcessReturn {
	
	private boolean success;
	private String message;
	
	public ProcessReturn(boolean success, String message) {
		this.success = success;
		this.message = message;
		
	}
	
	public ProcessReturn(boolean success) {
		this(success, null);
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	@Override
	public String toString() {
		return success + "@\"" +  message + "\"";
	}

}
