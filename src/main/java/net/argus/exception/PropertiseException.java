package net.argus.exception;

public class PropertiseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3276622351211387934L;

	public PropertiseException(String messErr) {
		System.err.print(messErr);
	}

}
