package net.argus.util;

public class Status {
	
	private Object[] objs;
	
	protected Status(Object ... objs) {
		this.objs = objs;
	}
	
	/**
	 * getObject
	 * @param off
	 * @return
	 */
	public Object getObject(int off) {
		if(off > objs.length)
			throw new IndexOutOfBoundsException();
		
		for(int i = off; i < objs.length; i++) {
			if(objs[i] instanceof Object) {
				return (Object) objs[i];
			}
		}
		return null;
	}

	
	/**
	 * getBoolean
	 * @param off
	 * @return
	 */
	public boolean getBoolean(int off) {
		if(off > objs.length)
			throw new IndexOutOfBoundsException();
		
		for(int i = off; i < objs.length; i++) {
			if(objs[i] instanceof Boolean) {
				return (boolean) objs[i];
			}
		}
		return false;
	}
	
	/**
	 * getInt
	 * @param off
	 * @return
	 */
	public int getInt(int off) {
		if(off > objs.length)
			throw new IndexOutOfBoundsException();
		
		for(int i = off; i < objs.length; i++) {
			if(objs[i] instanceof Integer) {
				return (int) objs[i];
			}
		}
		return -1;
	}
	
	/**
	 * getString
	 * @param off
	 * @return
	 */
	public String getString(int off) {
		if(off > objs.length)
			throw new IndexOutOfBoundsException();
		
		for(int i = off; i < objs.length; i++) {
			if(objs[i] instanceof String) {
				return (String) objs[i];
			}
		}
		return null;
	}
	
	public Object getObject() {return getObject(0);}
	public boolean getBoolean() {return getBoolean(0);}
	public int getInt() {return getInt(0);}
	public String getString() {return getString(0);}
	
	public Object get(int index) {return objs[index];}

}
