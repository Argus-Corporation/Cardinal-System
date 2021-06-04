package net.argus.util;

public class DoubleStock <F, S> {
	
	private F f;
	private S s;
	
	public DoubleStock() {}
	
	public DoubleStock(F f, S s) {
		set(f, s);
	}
	
	public void set(F f, S s) {
		setFirst(f);
		setSecond(s);
	}
	
	public void setFirst(F f) {this.f = f;}
	public void setSecond(S s) {this.s = s;}
	
	public F getFirst() {return f;}
	public S getSecond() {return s;}

}
