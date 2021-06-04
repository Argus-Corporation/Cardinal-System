package net.argus.exception;

public class PositionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4633862574245014223L;

	public PositionException(int numLine, int numberAllLine) {
		if(numLine <= 0) {
			System.err.print(numLine + " est inferieur ou egale a 0 ");
		}else if(numLine >= numberAllLine) {
			System.err.print(numLine + " est superieur au nombre total de ligne " + numberAllLine + " ");
		}
	}

}
