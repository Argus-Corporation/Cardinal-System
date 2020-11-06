package net.argus.util;


public class Binary extends Number {
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	public Binary(String value) {
		if(isValid(value)) {
			this.value = value;
		}else {
			throw new NumberFormatException();
		}
	}
	
	public Binary(int value) {
		if(isValid(String.valueOf(value))) {
			this.value = String.valueOf(value);
		}else {
			throw new NumberFormatException();
		}
	}
	
	public Binary(long value) {
		if(isValid(String.valueOf(value))) {
			this.value = String.valueOf(value);
		}else {
			throw new NumberFormatException();
		}
	}
	
	public Binary(float value) {
		if(isValid(String.valueOf(value))) {
			this.value = String.valueOf(value);
		}else {
			throw new NumberFormatException();
		}
	}
	
	public Binary(double value) {
		if(isValid(String.valueOf(value))) {
			this.value = String.valueOf(value);
		}else {
			throw new NumberFormatException();
		}
	}
	
	public static boolean isValid(String o) {
		char[] ch = o.toCharArray();
		
		for(char caracter : ch)
			if(caracter != '0' && caracter != '1')
				return false;
		
		return true;
		
	}
	
	
	@Override
	public int intValue() {
		return Integer.valueOf(value);
	}

	@Override
	public long longValue() {
		return Long.valueOf(value);
	}

	@Override
	public float floatValue() {
		return Float.valueOf(value);
	}

	@Override
	public double doubleValue() {
		return Double.valueOf(value);
	}
	
	@Override
	public String toString() {
		return value;
	}

}
