package net.argus.exception;

public class SearchException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3276622351211387934L;

	public SearchException(String messErr) {
		System.err.print(messErr);
	}

}
