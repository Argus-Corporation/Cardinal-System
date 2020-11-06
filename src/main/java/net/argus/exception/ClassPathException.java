package net.argus.exception;

public class ClassPathException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6011071078962622619L;

	public ClassPathException(String mes) {
		System.err.print(mes + " ");
	}
	
}
