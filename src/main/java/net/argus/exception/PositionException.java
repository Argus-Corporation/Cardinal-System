package net.argus.exception;

public class PositionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4633862574245014223L;

	public PositionException(int numLine, int numberAllLine) {
		if(numLine <= 1) {
			System.err.println(numLine + " est inferieur ou egale a 1");
		}else if(numLine >= numberAllLine) {
			System.err.println(numLine + " est superieur au nombre total de ligne " + numberAllLine);
		}
	}

}
